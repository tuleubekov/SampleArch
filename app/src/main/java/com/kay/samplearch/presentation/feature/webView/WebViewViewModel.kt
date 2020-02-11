package com.kay.samplearch.presentation.feature.webView

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kay.samplearch.common.extensions.def
import com.kay.samplearch.presentation.base.BaseViewModel

class WebViewViewModel(launcher: WebViewLauncher) : BaseViewModel() {
    val mProgress = MutableLiveData<Boolean>().def(false)
    val mWebViewUrl = MutableLiveData<String>().def(launcher.url)
    val mToolbarTitle = MutableLiveData<String>().def(launcher.toolbarTitle)

    fun showProgress(showProgress: Boolean) {
        mProgress.value = showProgress
    }

}

@Suppress("UNCHECKED_CAST")
class WebViewViewModelFactory(
    val launcher: WebViewLauncher
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WebViewViewModel(launcher) as T
    }

}