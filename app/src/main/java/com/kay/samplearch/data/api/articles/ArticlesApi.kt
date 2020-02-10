package com.kay.samplearch.data.api.articles

import io.reactivex.Single
import retrofit2.http.GET

interface ArticlesApi {

    @GET("/posts")
    fun getArticles(): Single<ArticlesDto>
}