package com.tomgilbert.core.data.repository

import com.tomgilbert.core.model.FileSystemItem

interface FileSystemItemRepository {

    suspend fun getFolderContents(id: String): List<FileSystemItem>
}