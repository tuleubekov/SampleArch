package com.kay.samplearch.presentation.images

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.module.AppGlideModule
import com.kay.samplearch.BuildConfig
import com.kay.samplearch.di.Injector
import kotlinx.android.parcel.Parcelize
import java.io.InputStream

class GlideImageLoader : ImageLoader {

    override fun load(url: String?): ImageLoader.RequestBuilder = CustomGlideRequest(_url = url)

    override fun load(drawableId: Int): ImageLoader.RequestBuilder = CustomGlideRequest(_drawableId = drawableId)
}

@Parcelize
class CustomGlideRequest(
    val _url: String? = null,
    val _drawableId: Int = 0
) : ImageLoader.RequestBuilder() {

    init {
        this.url = _url
        this.drawableId = _drawableId
    }

    override fun into(imageView: ImageView) {
        if ((url == null || url?.isEmpty() == true) && drawableId == 0) {
            if (placeholderId != 0) {
                imageView.setImageResource(placeholderId)
            } else if (errorId != 0) {
                imageView.setImageResource(errorId)
            }
            return
        } else if (drawableId != 0) {
            imageView.setImageResource(drawableId)
        } else if (url?.startsWith("drawable://") == true) {
            try {
                imageView.setImageResource(url?.removePrefix("drawable://")?.toInt() ?: 0)
                return
            } catch (e: Exception) {
            }
        }

        makeRequest(imageView.context)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .onlyRetrieveFromCache(false)
            .into(imageView)
    }

    @SuppressLint("CheckResult")
    private fun makeRequest(context: Context): GlideRequest<Drawable> {
        val glide: GlideRequests = AppGlide.with(context)
        val requestCreator: GlideRequest<Drawable> = if (url != null && url!!.isNotEmpty())
            glide.load(url)
        else if (drawableId != 0) {
            glide.load(drawableId)
        } else if (placeholderId != 0) {
            glide.load(placeholderId)
        } else {
            glide.load(errorId)
        }

        if (isCenterCrop) {
            requestCreator.centerCrop().circleCrop()
        } else {
            requestCreator.centerInside()
        }

        if (drawable != null) {
            requestCreator.placeholder(drawable)
        }

        if (placeholderId != 0) {
            requestCreator.placeholder(
                ResourcesCompat.getDrawable(
                    context.resources,
                    placeholderId, null
                )!!
            )
        }
        if (errorId != 0) {
            requestCreator.error(
                ResourcesCompat.getDrawable(
                    context.resources,
                    errorId, null
                )!!
            )
        }
        if (roundingRadius != 0) {
            val bitmapTransformation = if (isCenterCrop) {
                CenterCrop()
            } else {
                CenterInside()
            }
            requestCreator.transforms(bitmapTransformation, RoundedCorners(roundingRadius))
        }
        if (listener != null) {
            requestCreator.listener(listener)
        }
        if (width != 0 && height != 0) {
            requestCreator.override(width, height)
        }
        if (size != 0) {
            requestCreator.override(size)
        }

        return requestCreator
    }
}


@GlideModule(glideName = "AppGlide")
class MyAppGlideModule : AppGlideModule() {
    private val pictureOkHttp = Injector.pictureOkHttp()

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        registry.replace(
            GlideUrl::class.java,
            InputStream::class.java,
            OkHttpUrlLoader.Factory(pictureOkHttp)
        )
    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        if (BuildConfig.DEBUG) {
            builder.setLogLevel(Log.DEBUG)
        }
        super.applyOptions(context, builder)
    }
}