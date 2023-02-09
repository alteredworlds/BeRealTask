package com.tomgilbert.core.glide
import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.tomgilbert.core.model.FileItem
import java.io.InputStream

@GlideModule
class BeRealTaskAppGlideModule : AppGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.prepend(
            FileItem::class.java,
            InputStream::class.java,
            FileItemLoaderFactory(context)
        )
    }
}