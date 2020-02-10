package com.kay.samplearch.di.modules

import android.content.Context
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
}