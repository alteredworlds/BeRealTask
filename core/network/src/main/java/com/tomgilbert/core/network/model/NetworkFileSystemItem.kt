package com.tomgilbert.core.network.model

data class NetworkFileSystemItem(
    val id: String,
    val parentId: String?,
    val name: String,
    val isDir: Boolean,
    val modificationDate: String,
    val size: Int?,
    val contentType: String?
)