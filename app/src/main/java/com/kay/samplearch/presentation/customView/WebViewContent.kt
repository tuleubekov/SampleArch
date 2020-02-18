package com.kay.samplearch.presentation.customView

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

class WebViewContent @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : WebView(context, attrs, defStyleAttr) {

    var showProgress: ((Boolean) -> Unit) = {}

    init {
        settings.javaScriptEnabled = true
        settings.setAppCacheEnabled(false)
        settings.cacheMode = WebSettings.LOAD_NO_CACHE

        webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                showProgress(true)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                showProgress(false)
            }
        }
    }

    fun setContent(htmlData: String) {
        loadDataWithBaseURL(null, htmlData, MIME_TYPE, UTF_8, null)
    }

    companion object {
        private const val MIME_TYPE = "text/html"
        private const val UTF_8 = "UTF-8"
    }
}