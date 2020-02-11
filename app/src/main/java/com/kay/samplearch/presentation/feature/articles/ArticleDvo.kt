package com.kay.samplearch.presentation.feature.articles

import com.kay.samplearch.presentation.images.ImageLoader

data class ArticleDvo(
    val title: String,
    val link: String,
    val catIcon: ImageLoader.RequestBuilder
)