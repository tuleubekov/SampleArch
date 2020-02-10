package com.kay.samplearch.presentation.feature.articles

import android.os.Bundle
import android.view.View
import com.kay.samplearch.R
import com.kay.samplearch.base.BaseFragment
import com.kay.samplearch.base.BaseViewModel
import kotlin.reflect.KClass

class ArticlesFragment : BaseFragment() {

    override val layoutId get() = R.layout.fragment_articles

    override fun provideViewModel(): Map<KClass<*>, () -> BaseViewModel> {
        return mapOf()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        fun newInstance(): ArticlesFragment {
            return ArticlesFragment()
        }
    }
}