package com.tomgilbert.core.data.testdoubles

import com.tomgilbert.core.network.BeRealTaskNetworkDataSource
import com.tomgilbert.core.network.model.NetworkFileSystemItem
import com.tomgilbert.core.network.model.NetworkUserData
import java.time.Instant

class TestBeRealTaskNetworkDataSource : BeRealTaskNetworkDataSource {
    override suspend fun getUserData(username: String, password: String): NetworkUserData {
        return if (username == USERNAME && password == PASSWORD) USERDATA else throw Exception("Unauthorised")
    }

    override suspend fun getFolderContents(folderId: String): List<NetworkFileSystemItem> {
        return if (folderId == "1") CONTENTS else emptyList()
    }

    companion object {
        val MODIFICATION_DATE = Instant.now().toString()

        const val USERNAME = "tinyt"
        const val PASSWORD = "letmein"
        const val PARENT_FOLDER_ID = "1"
        val USERDATA = NetworkUserData(
            "Tiny", "Tim", NetworkFileSystemItem(
                id = PARENT_FOLDER_ID,
                parentId = null,
                name = "Home",
                isDir = true,
                modificationDate = MODIFICATION_DATE,
                size = null,
                contentType = null
            )
        )

        val CONTENTS = listOf(
            NetworkFileSystemItem(
                id = "2",
                parentId = PARENT_FOLDER_ID,
                name = "Folder2",
                isDir = true,
                modificationDate = MODIFICATION_DATE,
                size = null,
                contentType = null
            ),
            NetworkFileSystemItem(
                id = "3",
                parentId = PARENT_FOLDER_ID,
                name = "File3",
                isDir = false,
                modificationDate = MODIFICATION_DATE,
                size = 12345,
                contentType = null
            )
        )
    }
}