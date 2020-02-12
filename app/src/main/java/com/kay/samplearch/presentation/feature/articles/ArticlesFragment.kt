package com.kay.samplearch.presentation.feature.articles

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.kay.samplearch.R
import com.kay.samplearch.presentation.base.BaseFragment
import com.kay.samplearch.presentation.base.BaseViewModel
import com.kay.samplearch.presentation.extensions.observe
import com.kay.samplearch.presentation.extensions.swapData
import com.kay.samplearch.presentation.extensions.visible
import com.kay.samplearch.presentation.base.adapter.CompositeAdapter
import com.kay.samplearch.presentation.extensions.setLightStatusBar
import kotlinx.android.synthetic.main.fragment_articles.*
import kotlin.reflect.KClass

class ArticlesFragment : BaseFragment() {

    override val layoutId get() = R.layout.fragment_articles

    private val vRefresh get() = fragment_articles_refresh
    private val vRecycler get() = fragment_articles_list

    override fun provideViewModel(): Map<KClass<*>, () -> BaseViewModel> {
        return mapOf(vmCreator(ArticlesViewModel::class))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.setLightStatusBar()
        vRecycler.layoutManager = LinearLayoutManager(context)

        vm(ArticlesViewModel::class).apply {
            vRecycler.adapter = CompositeAdapter.Builder()
                .add(ArticlesAdapter(
                    onClick = { onArticleClicked(it as ArticleDvo) }
                ))
                .build()

            vRefresh.setOnRefreshListener {
                observeArticles()
            }

            mProgress.observe(viewLifecycleOwner) {
                vRefresh.isRefreshing = it
            }

            mArticles.observe(viewLifecycleOwner) {
                vRecycler.swapData(it)
            }
        }
    }

    companion object {
        fun newInstance(): ArticlesFragment {
            return ArticlesFragment()
        }
    }
}