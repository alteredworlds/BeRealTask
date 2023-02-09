package com.tomgilbert.core.data.repository

import com.tomgilbert.core.data.model.asExternalModel
import com.tomgilbert.core.data.testdoubles.TestBeRealTaskNetworkDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NetworkUserDataRepositoryTest {

    @Test
    fun networkUserDataRepository_pulls_from_network() = runTest {
        val network = TestBeRealTaskNetworkDataSource()
        val repo = NetworkUserDataRepository(network)
        val results = repo.getUserData(
            TestBeRealTaskNetworkDataSource.USERNAME,
            TestBeRealTaskNetworkDataSource.PASSWORD
        )

        assertEquals(TestBeRealTaskNetworkDataSource.USERDATA.asExternalModel(), results)
    }
}