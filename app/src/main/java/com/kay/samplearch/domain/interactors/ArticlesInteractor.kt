package com.kay.samplearch.domain.interactors

import android.content.Context
import com.kay.samplearch.data.ApiHelper
import com.kay.samplearch.data.models.ArticlesModel
import com.kay.samplearch.domain.exceptions.errorHandlers.ErrorHandler
import com.kay.samplearch.domain.extensions.handleErrors
import com.kay.samplearch.domain.models.SupportInfoMapper
import io.reactivex.Single

interface ArticlesInteractor {

    fun observeArticles(): Single<ArticlesModel>
}

class ArticlesInteractorImpl(
    val apiHelper: ApiHelper,
    val errorHandler: ErrorHandler,
    val context: Context
) : ArticlesInteractor {

    private val mapper = SupportInfoMapper()

    override fun observeArticles(): Single<ArticlesModel> {
        return apiHelper.articlesApi.getArticles()
            .map { mapper.mapToArticlesModel(it) }
            .handleErrors(errorHandler)
    }
}