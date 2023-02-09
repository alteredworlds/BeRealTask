package com.tomgilbert.core.data.repository

import com.tomgilbert.core.data.model.asExternalModel
import com.tomgilbert.core.data.testdoubles.TestBeRealTaskNetworkDataSource
import com.tomgilbert.core.data.testdoubles.TestBeRealTaskNetworkDataSource.Companion.PARENT_FOLDER_ID
import com.tomgilbert.core.model.FileSystemItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NetworkFileSystemItemRepositoryTest {

    @Test
    fun networkFileSystemItemRepository_pulls_from_network() = runTest {
        val network = TestBeRealTaskNetworkDataSource()
        val repo = NetworkFileSystemItemRepository(network)
        val results = repo.getFolderContents(PARENT_FOLDER_ID)

        assertEquals(TestBeRealTaskNetworkDataSource.CONTENTS.asExternalModel(), results)

        val emptyResults = repo.getFolderContents("12345")
        assertEquals(emptyList<FileSystemItem>(), emptyResults)
    }
}