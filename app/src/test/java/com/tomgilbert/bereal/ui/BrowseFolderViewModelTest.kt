package com.tomgilbert.bereal.ui

import androidx.lifecycle.SavedStateHandle
import com.tomgilbert.core.data.repository.FileSystemItemRepository
import com.tomgilbert.core.model.FileSystemItem
import com.tomgilbert.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BrowseFolderViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: BrowseFolderViewModel

    @Test
    fun stateIsEmptyAndGetFolderContentsNotCalledWhenNoCurrentFolderSupplied() = runTest {
        val fileSystemItemRepository = object : FileSystemItemRepository {
            override suspend fun getFolderContents(id: String): List<FileSystemItem> {
                TODO("Not yet implemented")
            }
        }
        viewModel = BrowseFolderViewModel(fileSystemItemRepository, SavedStateHandle())
        assertEquals(BrowseScreenUiState.Empty, viewModel.folderContents.value)
    }

    @Test
    fun stateIsLoadingWhileValidLoadRunningThenChangesToSuccess() = runTest {
        val fileSystemItemRepository = object : FileSystemItemRepository {
            override suspend fun getFolderContents(id: String): List<FileSystemItem> {
                delay(100L)
                return emptyList()
            }
        }
        val savedStateHandle = SavedStateHandle().apply {
            folderId = "1"
        }
        viewModel = BrowseFolderViewModel(fileSystemItemRepository, savedStateHandle)
        assertEquals(BrowseScreenUiState.Loading, viewModel.folderContents.value)
        advanceTimeBy(200L)
        assertEquals(BrowseScreenUiState.Success(emptyList()), viewModel.folderContents.value)
    }
}