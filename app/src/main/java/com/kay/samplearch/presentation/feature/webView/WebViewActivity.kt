package com.kay.samplearch.presentation.feature.webView

import android.content.Intent
import android.os.Bundle
import com.kay.samplearch.common.extensions.replaceFragment
import com.kay.samplearch.presentation.base.BaseActivity

class WebViewActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createFragmentIfNotExists(savedInstanceState) {
            replaceFragment(WebViewFragment.newInstance(intent.getParcelableExtra(LAUNCHER)!!))
        }
    }

    companion object {
        const val LAUNCHER = "LAUNCHER"

        fun args(launcher: WebViewLauncher): Intent.() -> Unit = {
            putExtra(LAUNCHER, launcher)
        }
    }
}