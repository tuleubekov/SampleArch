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
import kotlinx.android.synthetic.main.fragment_articles.*
import kotlin.reflect.KClass

class ArticlesFragment : BaseFragment() {

    override val layoutId get() = R.layout.fragment_articles

    private val vProgress get() = fragment_articles_progress
    private val vRecycler get() = fragment_articles_list

    override fun provideViewModel(): Map<KClass<*>, () -> BaseViewModel> {
        return mapOf(vmCreator(ArticlesViewModel::class))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vRecycler.layoutManager = LinearLayoutManager(context)

        vm(ArticlesViewModel::class).apply {
            vRecycler.adapter = CompositeAdapter.Builder()
                .add(ArticlesAdapter(
                    onClick = { onArticleClicked(it as ArticleDvo) }
                ))
                .build()

            mProgress.observe(viewLifecycleOwner) {
                vProgress.visible(it)
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