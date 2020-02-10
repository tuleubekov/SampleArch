package com.kay.samplearch.data

import com.kay.samplearch.data.api.articles.ArticlesApi

interface ApiHelper {

    val articlesApi: ArticlesApi
}

class ApiHelperImpl(
    override val articlesApi: ArticlesApi
) : ApiHelper