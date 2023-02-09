package com.tomgilbert.core.network.di

import com.tomgilbert.core.network.AuthorizationProvider
import com.tomgilbert.core.network.BeRealTaskNetworkDataSource
import com.tomgilbert.core.network.retrofit.RetrofitAuthorizationProvider
import com.tomgilbert.core.network.retrofit.RetrofitBeRealTaskNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class NetworkModule {

    @Singleton
    @Binds
    abstract fun bindBeRealTaskNetworkDataSource(impl: RetrofitBeRealTaskNetwork): BeRealTaskNetworkDataSource

    @Singleton
    @Binds
    abstract fun bindAuthorizationProvider(impl: RetrofitAuthorizationProvider): AuthorizationProvider
}