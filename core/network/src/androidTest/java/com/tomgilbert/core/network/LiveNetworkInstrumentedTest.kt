package com.tomgilbert.core.network

import com.tomgilbert.core.network.retrofit.BasicAuthInterceptor
import com.tomgilbert.core.network.retrofit.RetrofitAuthorizationProvider
import com.tomgilbert.core.network.retrofit.RetrofitBeRealTaskNetwork
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class LiveNetworkInstrumentedTest {

    private val userName = "" // fill in with real value during dev testing
    private val password = "" // fill in with real value during dev testing

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private val authInterceptor = BasicAuthInterceptor(RetrofitAuthorizationProvider())

    @Test(expected = HttpException::class)
    fun getUserDataExpectAuthFailure(): Unit = runBlocking {
        val test = RetrofitBeRealTaskNetwork(authInterceptor)
        test.getUserData("fred", "no")
    }

    // The following two tests require valid user credentials in userName, password
    //  to succeed.
//    @Test
//    fun getUserDataExpectSuccess(): Unit = runBlocking {
//        val test = RetrofitBeRealTaskNetwork(authInterceptor)
//        val result = test.getUserData(userName, password)
//        assertEquals(result.firstName, "Noel")
//    }
//
//    @Test
//    fun getRootFolderContents(): Unit = runTest {
//        val test = RetrofitBeRealTaskNetwork(authInterceptor)
//        val userData = test.getUserData(userName, password)
//        assertEquals(userData.firstName, "Noel")
//
//        val contents = test.getFolderContents(userData.rootItem.id)
//        assertEquals(true, contents.isNotEmpty())
//    }
}