package com.tomgilbert.core.glide

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.*
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader
import com.tomgilbert.core.model.FileItem
import com.tomgilbert.core.network.AuthorizationProvider
import com.tomgilbert.core.network.retrofit.BASE_URL
import java.io.InputStream

class FileItemModelLoader(
    private val authorizationProvider: AuthorizationProvider,
    concreteLoader: ModelLoader<GlideUrl, InputStream>,
    modelCache: ModelCache<FileItem, GlideUrl>?
) : BaseGlideUrlLoader<FileItem>(concreteLoader, modelCache) {

    override fun handles(model: FileItem): Boolean {
        return model.contentType?.contains("image/") == true
    }

    override fun getHeaders(
        model: FileItem?,
        width: Int,
        height: Int,
        options: Options?
    ): Headers? {
        return LazyHeaders.Builder()
            .addHeader("Authorization", authorizationProvider.credentials)
            .build()
    }

    override fun getUrl(model: FileItem?, width: Int, height: Int, options: Options?): String {
        return model?.let {
            "$BASE_URL/items/${it.id}/data"
        } ?: ""
    }
}