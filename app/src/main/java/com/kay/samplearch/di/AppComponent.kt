package com.kay.samplearch.di

import android.content.Context
import com.kay.samplearch.common.ThreadExecutors
import com.kay.samplearch.di.modules.*
import com.kay.samplearch.domain.interactors.ArticlesInteractor
import com.kay.samplearch.presentation.images.ImageLoader
import dagger.Component
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Component(modules = [
    PresentationModule::class,
    DataModule::class,
    ApiModule::class,
    ErrorHandlerModule::class,
    GsonModule::class,
    FeatureModule::class
])
@Singleton
interface AppComponent {

    val context: Context

    val threadExecutors: ThreadExecutors

    val articlesInteractor: ArticlesInteractor

    val imageLoader: ImageLoader

    @ApiModule.ApiModulePictureHttp
    fun pictureOkHttp(): OkHttpClient
}