package com.tomgilbert.core.network.retrofit

import com.google.gson.GsonBuilder
import com.tomgilbert.core.network.AuthorizationProvider
import com.tomgilbert.core.network.BeRealTaskNetworkDataSource
import com.tomgilbert.core.network.model.NetworkFileSystemItem
import com.tomgilbert.core.network.model.NetworkUserData
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject
import javax.inject.Singleton

private interface RetrofitBeRealTaskApi {
    @GET("/me")
    suspend fun getUserData(): NetworkUserData

    @GET("/items/{id}")
    suspend fun getFolderContents(
        @Path("id") folderId: String
    ): List<NetworkFileSystemItem>
}

const val BASE_URL = "http://163.172.147.216:8080"

class BasicAuthInterceptor @Inject constructor(
    authorizationProvider: AuthorizationProvider
) : Interceptor, AuthorizationProvider by authorizationProvider {

    override fun intercept(chain: Interceptor.Chain): Response {
        val authRequest = chain.request().newBuilder()
            .header("Authorization", credentials).build()
        return chain.proceed(authRequest)
    }
}

@Singleton
class RetrofitBeRealTaskNetwork @Inject constructor(
    private val authInterceptor: BasicAuthInterceptor
) : BeRealTaskNetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .build(),
        )
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
        .create(RetrofitBeRealTaskApi::class.java)

    override suspend fun getUserData(username: String, password: String): NetworkUserData {
        authInterceptor.setUserPassword(username, password)
        return networkApi.getUserData()
    }

    override suspend fun getFolderContents(folderId: String): List<NetworkFileSystemItem> =
        networkApi.getFolderContents(folderId)
}