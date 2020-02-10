package com.kay.samplearch

import android.app.Application
import com.kay.samplearch.di.AppComponentInjector

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppComponentInjector.create(this)
    }
}