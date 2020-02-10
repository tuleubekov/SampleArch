package com.kay.samplearch.di.modules

import android.content.Context
import com.kay.samplearch.domain.exceptions.errorHandlers.ErrorHandler
import com.kay.samplearch.domain.exceptions.errorHandlers.GeneralErrorHandler
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ErrorHandlerModule {

    @Provides
    @Singleton
    fun provideErrorHandler(
        context: Context
    ): ErrorHandler = GeneralErrorHandler(context = context)
}