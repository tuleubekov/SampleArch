package com.kay.samplearch.presentation.feature.webView

import android.os.Bundle
import android.view.View
import com.kay.samplearch.R
import com.kay.samplearch.presentation.extensions.observe
import com.kay.samplearch.presentation.extensions.visible
import com.kay.samplearch.presentation.base.BaseFragment
import com.kay.samplearch.presentation.base.BaseViewModel
import com.kay.samplearch.presentation.base.launcher
import com.kay.samplearch.presentation.extensions.setLightStatusBar
import kotlinx.android.synthetic.main.fragment_web_view.*
import kotlin.reflect.KClass

class WebViewFragment : BaseFragment () {

    override val layoutId get() = R.layout.fragment_web_view

    private val vProgress get() = fragment_web_view_progress
    private val vToolbar get() = fragment_web_view_toolbar
    private val vWebView get() = fragment_web_view

    override fun provideViewModel(): Map<KClass<*>, () -> BaseViewModel> {
        return mapOf(vmCreator(WebViewViewModel::class, WebViewViewModelFactory(launcher())))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.setLightStatusBar()
        vWebView.requestFocus(View.FOCUS_DOWN)

        vm(WebViewViewModel::class).apply {
            mProgress.observe(viewLifecycleOwner) {
                vProgress.visible(it)
            }

            mToolbarTitle.observe(viewLifecycleOwner) {
                vToolbar.setTitle(it)
            }

            mWebViewUrl.observe(viewLifecycleOwner) {
                vWebView.loadUrl(it)
            }

            vWebView.showProgress = {
                if (isAdded) { showProgress(it) }
            }
        }

    }

    companion object {
        fun newInstance(launcher: WebViewLauncher): WebViewFragment {
            return WebViewFragment().launcher(launcher)
        }
    }
}