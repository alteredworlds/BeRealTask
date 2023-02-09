package com.tomgilbert.bereal.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomgilbert.bereal.navigation.ARG_ID
import com.tomgilbert.core.data.repository.FileSystemItemRepository
import com.tomgilbert.core.model.FileItem
import com.tomgilbert.core.model.FileSystemItem
import com.tomgilbert.core.model.FolderItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import javax.inject.Inject


internal var SavedStateHandle.folderId: String?
    get() = this[ARG_ID]
    set(value) {
        set(ARG_ID, value)
    }


@HiltViewModel
class BrowseFolderViewModel @Inject constructor(
    private val fileSystemItemRepository: FileSystemItemRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val currentFolderId = savedStateHandle.folderId

    private val _folderContents = MutableStateFlow<BrowseScreenUiState>(BrowseScreenUiState.Loading)
    val folderContents: StateFlow<BrowseScreenUiState> = _folderContents

    private val localDateFormatter = DateTimeFormatter
        .ofLocalizedDate(FormatStyle.LONG).withZone(ZoneId.systemDefault())

    init {
        viewModelScope.launch {
            Timber.i("launching BrowseFolderViewModel with $currentFolderId")
            _folderContents.value = currentFolderId?.let { id ->
                try {
                    BrowseScreenUiState.Success(
                        fileSystemItemRepository.getFolderContents(id)
                            .map { it.withLocalDisplayDate() }
                    )
                } catch (e: Throwable) {
                    BrowseScreenUiState.Error(e)
                }
            } ?: BrowseScreenUiState.Empty
        }
    }

    private fun FileSystemItem.withLocalDisplayDate(): FileSystemItem {
        return when (this) {
            is FileItem -> copy(modificationDate = asLocalDisplayDate(modificationDate))
            is FolderItem -> copy(modificationDate = asLocalDisplayDate(modificationDate))
        }
    }

    private fun asLocalDisplayDate(isoDateString: String): String {
        return localDateFormatter.format(
            DateTimeFormatter.ISO_INSTANT.parse(
                isoDateString,
                Instant::from
            )
        )
    }
}

sealed interface BrowseScreenUiState {
    object Empty : BrowseScreenUiState
    object Loading : BrowseScreenUiState
    data class Success(val folderContents: List<FileSystemItem>) : BrowseScreenUiState
    data class Error(val exception: Throwable? = null) : BrowseScreenUiState
}