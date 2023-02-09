package com.tomgilbert.core.model

data class FolderItem(
    override val id: String,
    override val parentId: String?,
    override val name: String,
    override val modificationDate: String
): FileSystemItem