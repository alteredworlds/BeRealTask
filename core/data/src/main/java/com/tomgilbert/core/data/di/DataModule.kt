package com.tomgilbert.core.data.di

import com.tomgilbert.core.data.repository.FileSystemItemRepository
import com.tomgilbert.core.data.repository.NetworkFileSystemItemRepository
import com.tomgilbert.core.data.repository.NetworkUserDataRepository
import com.tomgilbert.core.data.repository.UserDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsFileSystemItemRepository(
        repository: NetworkFileSystemItemRepository
    ): FileSystemItemRepository

    @Binds
    fun bindsUserDataRepository(
        repository: NetworkUserDataRepository
    ): UserDataRepository
}