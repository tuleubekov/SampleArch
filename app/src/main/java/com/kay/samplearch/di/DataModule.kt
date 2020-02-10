package com.kay.samplearch.di

import com.kay.samplearch.common.ThreadExecutors
import com.kay.samplearch.common.toAppExecutor
import com.kay.samplearch.data.TestUrlHelper
import com.kay.samplearch.data.UrlHelper
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.ComputationScheduler
import io.reactivex.internal.schedulers.IoScheduler
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideThreadExecutors(): ThreadExecutors = ThreadExecutors(
        ComputationScheduler().toAppExecutor(),
        IoScheduler().toAppExecutor(),
        AndroidSchedulers.mainThread().toAppExecutor()
    )

    @Provides
    @Singleton
    fun provideUrlHelper(): UrlHelper {
        return TestUrlHelper()
    }

}