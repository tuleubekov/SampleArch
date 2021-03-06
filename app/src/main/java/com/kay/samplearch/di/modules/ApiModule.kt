package com.kay.samplearch.di.modules

import android.content.Context
import com.google.gson.Gson
import com.kay.samplearch.BuildConfig
import com.kay.samplearch.data.ApiHelper
import com.kay.samplearch.data.ApiHelperImpl
import com.kay.samplearch.data.UrlHelper
import com.kay.samplearch.data.api.articles.ArticlesApi
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module(includes = [
    GsonModule::class
])
class ApiModule {
    private val isDebug get() = BuildConfig.DEBUG

    @Provides
    @Singleton
    @ApiModuleGeneralHttp
    fun provideSimpleOkHttpClient(): OkHttpClient {
        val bodyInterceptor = HttpLoggingInterceptor()
        bodyInterceptor.level =
            if (isDebug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        val headerInterceptor = HttpLoggingInterceptor()
        headerInterceptor.level =
            if (isDebug) HttpLoggingInterceptor.Level.HEADERS else HttpLoggingInterceptor.Level.NONE

        val builder = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(bodyInterceptor)
            .addInterceptor(headerInterceptor)

        return builder.build()
    }

    @Provides
    @Singleton
    @ApiModulePictureHttp
    fun providePictureOkHttp(context: Context, @ApiModuleGeneralHttp okHttpClient: OkHttpClient): OkHttpClient {
        return okHttpClient.newBuilder()
            .addNetworkInterceptor { chain ->
                val originalResponse = chain.proceed(chain.request())
                originalResponse
                    .newBuilder()
                    .header("Cache-Control", "max-age=" + 60 * 60 * 24 * 365)
                    .build()
            }
            .cache(Cache(context.cacheDir, Integer.MAX_VALUE.toLong()))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(@ApiModuleGeneralHttp okHttpClient: OkHttpClient, gson: Gson, urlHelper: UrlHelper): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(urlHelper.getMainUrl())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiHelper(
        generalRetrofit: Retrofit
    ): ApiHelper = ApiHelperImpl(
        articlesApi = generalRetrofit.create(ArticlesApi::class.java)
    )

    companion object {
        const val TIMEOUT = 600L
    }

    @Qualifier
    @MustBeDocumented
    @Retention
    annotation class ApiModuleGeneralHttp

    @Qualifier
    @MustBeDocumented
    @Retention
    annotation class ApiModulePictureHttp
}