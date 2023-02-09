package com.tomgilbert.core.glide

import android.content.Context
import com.bumptech.glide.load.model.*
import com.tomgilbert.core.model.FileItem
import com.tomgilbert.core.network.AuthorizationProvider
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.io.InputStream

class FileItemLoaderFactory(private val context: Context) :
    ModelLoaderFactory<FileItem, InputStream> {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface FileItemLoaderFactoryEntryPoint {
        fun authorizationProvider(): AuthorizationProvider
    }

    private val authorizationProvider
        get() = EntryPoints.get(context, FileItemLoaderFactoryEntryPoint::class.java)
            .authorizationProvider()

    private val modelCache = ModelCache<FileItem, GlideUrl>(500)

    override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<FileItem, InputStream> {
        return FileItemModelLoader(
            authorizationProvider,
            multiFactory.build(GlideUrl::class.java, InputStream::class.java),
            modelCache
        )
    }

    override fun teardown() {
        // Do nothing
    }
}