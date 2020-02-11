package com.kay.samplearch.presentation.feature.articles

import androidx.lifecycle.MutableLiveData
import com.kay.samplearch.common.extensions.startNewActivity
import com.kay.samplearch.presentation.base.BaseViewModel
import com.kay.samplearch.di.Injector
import com.kay.samplearch.presentation.feature.webView.WebViewActivity
import com.kay.samplearch.presentation.feature.webView.WebViewLauncher

class ArticlesViewModel : BaseViewModel() {
    private val articlesInteractor = Injector.articlesInteractor
    private val imageLoader = Injector.imageLoader

    val mArticles = MutableLiveData<List<ArticleDvo>>()
    val mProgress = MutableLiveData<Boolean>()

    init {
        mProgress.value = true
        articlesInteractor.observeArticles()
            .scheduleOnApi()
            .map {
                it.map { model ->
                    ArticleDvo(
                        title = model.title,
                        link = model.link,
                        catIcon = imageLoader.load(model.catIcon)
                    )
                }
            }
            .doOnSuccess { mArticles.value = it }
            .doFinally { mProgress.value = false }
            .bindSubscribe()
    }

    fun onArticleClicked(dvo: ArticleDvo) {
        withActivity {
            it.startNewActivity(WebViewActivity::class, WebViewActivity.args(
                launcher = WebViewLauncher(
                    url = dvo.link,
                    toolbarTitle = dvo.title
                )
            ))
        }
    }
}