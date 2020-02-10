package com.kay.samplearch.di.modules

import android.content.Context
import com.kay.samplearch.data.ApiHelper
import com.kay.samplearch.domain.exceptions.errorHandlers.ErrorHandler
import com.kay.samplearch.domain.interactors.ArticlesInteractor
import com.kay.samplearch.domain.interactors.ArticlesInteractorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FeatureModule {

    @Provides
    @Singleton
    fun provideArticlesInteractor(
        apiHelper: ApiHelper,
        context: Context,
        errorHandler: ErrorHandler
    ): ArticlesInteractor {
        return ArticlesInteractorImpl(apiHelper, errorHandler, context)
    }
}