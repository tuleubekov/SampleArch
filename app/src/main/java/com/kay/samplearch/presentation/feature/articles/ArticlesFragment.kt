package com.kay.samplearch.presentation.feature.articles

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.kay.samplearch.R
import com.kay.samplearch.presentation.base.BaseFragment
import com.kay.samplearch.presentation.base.BaseViewModel
import com.kay.samplearch.common.extensions.observe
import com.kay.samplearch.common.extensions.setItems
import com.kay.samplearch.common.extensions.visible
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
        vRecycler.adapter = ArticlesAdapter(onClick = { Log.e("______", "click title= ${it.title}") })

        vm(ArticlesViewModel::class).apply {
            mProgress.observe(viewLifecycleOwner) {
                vProgress.visible(it)
            }

            mArticles.observe(viewLifecycleOwner) {
                vRecycler.setItems(it)
            }
        }
    }

    companion object {
        fun newInstance(): ArticlesFragment {
            return ArticlesFragment()
        }
    }
}