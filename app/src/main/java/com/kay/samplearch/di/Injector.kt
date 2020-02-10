package com.kay.samplearch.di

import android.content.Context

object AppComponentInjector {
    private lateinit var _appComponent: AppComponent

    val appComponent get() = _appComponent

    fun create(context: Context): AppComponent {
        val appComponent = DaggerAppComponent.builder()
            .presentationModule(PresentationModule(context))
            .build()

        _appComponent = appComponent

        return appComponent
    }
}

val Any.Injector: AppComponent by lazy { AppComponentInjector.appComponent }