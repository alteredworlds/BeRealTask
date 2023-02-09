package com.tomgilbert.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FileItem(
    override val id: String,
    override val parentId: String?,
    override val name: String,
    override val modificationDate: String,
    val size: Int?,
    val contentType: String?
): FileSystemItem, Parcelable