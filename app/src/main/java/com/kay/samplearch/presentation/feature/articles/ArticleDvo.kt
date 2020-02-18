package com.kay.samplearch.presentation.feature.articles

import com.kay.samplearch.presentation.base.adapter.model.IAdapterItem
import com.kay.samplearch.presentation.images.ImageLoader
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArticleDvo(
    val title: String,
    val link: String,
    val content: String,
    val catIcon: ImageLoader.RequestBuilder
): IAdapterItem