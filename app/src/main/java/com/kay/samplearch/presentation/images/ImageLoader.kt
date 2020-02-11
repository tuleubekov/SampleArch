package com.kay.samplearch.presentation.images

import android.graphics.drawable.Drawable
import android.os.Parcelable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.target.Target
import com.kay.samplearch.R

interface ImageLoader {

    fun load(url: String?): RequestBuilder

    fun load(@DrawableRes drawableId: Int): RequestBuilder

    abstract class RequestBuilder : Parcelable {
        protected var url: String? = null
        protected var isAsCircle = false
        protected var isCenterCrop = true
        protected var roundingRadius = 0
        protected var listener: RequestListener? = null
        protected var width = 0
        protected var height = 0
        protected var size = 0
        protected var drawable: Drawable? = null
        @DrawableRes protected var drawableId = 0
        @DrawableRes protected var placeholderId = R.drawable.ic_placeholder
        @DrawableRes protected var errorId = 0

        fun placeholder(@DrawableRes placeholderId: Int): RequestBuilder {
            this.placeholderId = placeholderId
            return this
        }

        fun nonCenterCrop(): RequestBuilder {
            isCenterCrop = false
            return this
        }

        fun placeholder(drawable: Drawable): RequestBuilder {
            this.drawable = drawable
            return this
        }

        fun error(@DrawableRes errorId: Int): RequestBuilder {
            this.errorId = errorId
            return this
        }

        fun asCircle(): RequestBuilder {
            isAsCircle = true
            return this
        }

        fun roundedCorners(roundingRadius: Int): RequestBuilder {
            this.roundingRadius = roundingRadius
            return this
        }

        fun listener(listener: RequestListener): RequestBuilder {
            this.listener = listener
            return this
        }

        fun override(width: Int, height: Int): RequestBuilder {
            this.width = width
            this.height = height
            return this
        }

        fun override(size: Int): RequestBuilder {
            this.size = size
            return this
        }

        abstract fun into(imageView: ImageView)
    }

    interface RequestListener : com.bumptech.glide.request.RequestListener<Drawable> {

        override fun onLoadFailed(
            e: GlideException?,
            model: Any,
            target: Target<Drawable>,
            isFirstResource: Boolean
        ): Boolean

        override fun onResourceReady(
            resource: Drawable,
            model: Any,
            target: Target<Drawable>,
            dataSource: DataSource,
            isFirstResource: Boolean
        ): Boolean

    }
}