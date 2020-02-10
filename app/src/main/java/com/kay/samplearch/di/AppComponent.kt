package com.kay.samplearch.di

import android.content.Context
import com.kay.samplearch.common.ThreadExecutors
import dagger.Component
import javax.inject.Singleton

@Component(modules = [
    PresentationModule::class,
    DataModule::class
])
@Singleton
interface AppComponent {

    val context: Context

    val threadExecutors: ThreadExecutors
}