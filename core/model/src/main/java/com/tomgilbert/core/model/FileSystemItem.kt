package com.tomgilbert.core.model

sealed interface FileSystemItem {
    val id: String
    val parentId: String?
    val name: String
    val modificationDate: String
}