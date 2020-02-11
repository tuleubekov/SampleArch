package com.kay.samplearch.presentation.feature.articles

import androidx.lifecycle.MutableLiveData
import com.kay.samplearch.base.BaseViewModel
import com.kay.samplearch.data.models.ArticleModel
import com.kay.samplearch.di.Injector

class ArticlesViewModel : BaseViewModel() {
    private val articlesInteractor = Injector.articlesInteractor

    val mArticles = MutableLiveData<List<ArticleModel>>()
    val mProgress = MutableLiveData<Boolean>()

    init {
        mProgress.value = true
        articlesInteractor.observeArticles()
            .scheduleOnApi()
            .doOnSuccess { mArticles.value = it }
            .doFinally { mProgress.value = false }
            .bindSubscribe()
    }
}