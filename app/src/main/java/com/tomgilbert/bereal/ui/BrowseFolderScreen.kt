@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalComposeUiApi::class)

package com.tomgilbert.bereal.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.tomgilbert.bereal.R
import com.tomgilbert.bereal.ui.theme.BeRealTaskTheme
import com.tomgilbert.core.model.FileItem
import com.tomgilbert.core.model.FileSystemItem
import com.tomgilbert.core.model.FolderItem

@Composable
fun BrowseFolderScreen(
    onOpenSubFolder: (String) -> Unit,
    viewModel: BrowseFolderViewModel = hiltViewModel()
) {
    BrowseFolderScreen(
        folderContents = viewModel.folderContents.collectAsStateWithLifecycle(),
        onOpenSubFolder = onOpenSubFolder
    )
}

@Composable
fun BrowseFolderScreen(
    folderContents: State<BrowseScreenUiState>,
    onOpenSubFolder: (String) -> Unit
) {
    Box(modifier = Modifier.padding(16.dp)) {
        when (val value = folderContents.value) {
            BrowseScreenUiState.Empty -> Text(text = stringResource(id = R.string.empty))
            is BrowseScreenUiState.Error -> Text(
                text = stringResource(
                    R.string.error_with_info,
                    value.exception?.localizedMessage ?: ""
                )
            )
            BrowseScreenUiState.Loading -> Text(text = stringResource(id = R.string.loading))
            is BrowseScreenUiState.Success -> FolderItemList(
                list = value.folderContents,
                onOpenSubFolder = onOpenSubFolder
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FolderItemList(
    list: List<FileSystemItem>,
    onOpenSubFolder: (String) -> Unit
) {
    var showFullScreenImage: FileItem? by rememberSaveable { mutableStateOf(null) }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        items(
            items = list,
            key = { it.id }
        ) { item ->
            when (item) {
                is FolderItem -> FolderItemRow(item, onOpenSubFolder)
                is FileItem -> FileItemRow(item) {
                    showFullScreenImage = it
                }
            }
        }
    }
    showFullScreenImage?.let { imageFile ->
        Dialog(
            onDismissRequest = {
                showFullScreenImage = null
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false
            )
        ) {
            GlideImage(
                model = imageFile,
                contentDescription = stringResource(id = R.string.image_description),
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


@Composable
fun FileItemRow(item: FileItem, onClickFileItem: (FileItem) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClickFileItem(item)
            }) {
        Icon(
            painterResource(
                id = R.drawable.outline_article_24
            ),
            contentDescription = stringResource(
                id = R.string.file_description
            )
        )
        Spacer(modifier = Modifier.padding(start = 16.dp))
        Column {
            Text(text = item.name, style = MaterialTheme.typography.body2)
            Text(
                text = stringResource(id = R.string.modified_at, item.modificationDate),
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Composable
fun FolderItemRow(
    item: FolderItem,
    onOpenSubFolder: (String) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onOpenSubFolder(item.id)
            }) {
        Icon(
            painter = painterResource(id = R.drawable.outline_folder_24),
            contentDescription = stringResource(
                id = R.string.folder_description
            )
        )
        Spacer(modifier = Modifier.padding(start = 16.dp))
        Column {
            Text(text = item.name, style = MaterialTheme.typography.body2)
            Text(
                text = stringResource(id = R.string.modified_at, item.modificationDate),
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val state = remember { mutableStateOf(BrowseScreenUiState.Loading) }
    BeRealTaskTheme {
        BrowseFolderScreen(
            folderContents = state,
            onOpenSubFolder = { }
        )
    }
}