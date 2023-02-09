package com.tomgilbert.core.data.model

import com.tomgilbert.core.model.FileItem
import com.tomgilbert.core.model.FolderItem
import com.tomgilbert.core.network.model.NetworkFileSystemItem
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Instant

class FileSystemItemKtTest {

    @Test
    fun networkFileSystemItem_can_be_mapped_to_fileItem() {
        val now = Instant.now().toString()
        val networkFileSystemItem = NetworkFileSystemItem(
            id = "2",
            parentId = "1",
            name = "test.jpg",
            isDir = false,
            modificationDate = now,
            size = 34567,
            contentType = "image/jpeg"
        )
        val fileItem = FileItem(
            id = "2",
            parentId = "1",
            name = "test.jpg",
            modificationDate = now,
            size = 34567,
            contentType = "image/jpeg"
        )
        assertEquals(fileItem, networkFileSystemItem.asExternalModel())
    }

    @Test
    fun networkFileSystemItem_can_be_mapped_to_folderItem() {
        val now = Instant.now().toString()
        val networkFileSystemItem = NetworkFileSystemItem(
            id = "2",
            parentId = "1",
            name = "Folder2",
            isDir = true,
            modificationDate = now,
            size = null,
            contentType = null
        )
        val folderItem = FolderItem(
            id = "2",
            parentId = "1",
            name = "Folder2",
            modificationDate = now
        )
        assertEquals(folderItem, networkFileSystemItem.asExternalModel())
    }
}