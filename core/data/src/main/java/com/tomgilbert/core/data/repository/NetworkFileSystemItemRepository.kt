package com.tomgilbert.core.data.repository

import com.tomgilbert.core.data.model.asExternalModel
import com.tomgilbert.core.model.FileSystemItem
import com.tomgilbert.core.network.BeRealTaskNetworkDataSource
import javax.inject.Inject

class NetworkFileSystemItemRepository @Inject constructor(
    private val network: BeRealTaskNetworkDataSource
) : FileSystemItemRepository {

    override suspend fun getFolderContents(id: String): List<FileSystemItem> {
        return network.getFolderContents(id)
            .asExternalModel()
    }
}