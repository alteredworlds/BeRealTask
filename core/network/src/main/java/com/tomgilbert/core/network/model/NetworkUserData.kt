package com.tomgilbert.core.network.model

data class NetworkUserData(
    val firstName: String,
    val lastName: String,
    val rootItem: NetworkFileSystemItem
)