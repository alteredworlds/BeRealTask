package com.tomgilbert.core.data.model

import com.tomgilbert.core.model.FileItem
import com.tomgilbert.core.model.FileSystemItem
import com.tomgilbert.core.model.FolderItem
import com.tomgilbert.core.network.model.NetworkFileSystemItem

fun NetworkFileSystemItem.asExternalModel(): FileSystemItem {
    return if (isDir) {
        FolderItem(id, parentId, name, modificationDate)
    } else {
        FileItem(id, parentId, name, modificationDate, size, contentType)
    }
}

fun List<NetworkFileSystemItem>.asExternalModel(): List<FileSystemItem> =
    map { it.asExternalModel() }