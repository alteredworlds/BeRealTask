package com.tomgilbert.core.network

import com.tomgilbert.core.network.model.NetworkFileSystemItem
import com.tomgilbert.core.network.model.NetworkUserData

interface BeRealTaskNetworkDataSource {

    suspend fun getUserData(username: String, password: String): NetworkUserData

    suspend fun getFolderContents(folderId: String): List<NetworkFileSystemItem>
}