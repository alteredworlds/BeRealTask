package com.tomgilbert.core.network

import com.tomgilbert.core.network.retrofit.RetrofitBeRealTaskNetwork
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@HiltAndroidTest
class LiveNetworkInstrumentedTest {

    private val userName = "" // fill in with real value during dev testing
    private val password = "" // fill in with real value during dev testing

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test(expected = HttpException::class)
    fun getUserDataExpectAuthFailure(): Unit = runBlocking {
        val test = RetrofitBeRealTaskNetwork()
        test.getUserData("fred", "no")
    }

    @Test
    fun getUserDataExpectSuccess(): Unit = runBlocking {
        val test = RetrofitBeRealTaskNetwork()
        val result = test.getUserData(userName, password)
        assertEquals(result.firstName, "Noel")
    }

    @Test
    fun getRootFolderContents(): Unit = runTest {
        val test = RetrofitBeRealTaskNetwork()
        val userData = test.getUserData(userName, password)
        assertEquals(userData.firstName, "Noel")

        val contents = test.getFolderContents(userData.rootItem.id)
        assertEquals(true, contents.isNotEmpty())
    }
}