package com.kay.samplearch.di.modules

import android.content.Context
import com.kay.samplearch.presentation.images.GlideImageLoader
import com.kay.samplearch.presentation.images.ImageLoader
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresentationModule(
    val context: Context
) {

    @Singleton
    @Provides
    fun context(): Context = context

    @Singleton
    @Provides
    fun provideImageLoader(): ImageLoader = GlideImageLoader()
}