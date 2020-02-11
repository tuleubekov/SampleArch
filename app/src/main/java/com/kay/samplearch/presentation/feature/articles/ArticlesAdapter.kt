package com.kay.samplearch.presentation.feature.articles

import android.view.ViewGroup
import com.kay.samplearch.R
import com.kay.samplearch.presentation.base.BaseAdapter
import kotlinx.android.synthetic.main.item_article.*

class ArticlesAdapter(
    val onClick: (ArticleDvo) -> Unit
) : BaseAdapter<ArticleDvo>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<ArticleDvo> {
        return ArticleViewHolder(parent, onClick)
    }
}

class ArticleViewHolder(
    viewGroup: ViewGroup,
    val onClick: (ArticleDvo) -> Unit
) : BaseAdapter.ViewHolder<ArticleDvo>(R.layout.item_article, viewGroup) {

    private val vTitle get() = item_article_title
    private val vCatIcon get() = item_article_cat_icon

    override fun show(data: ArticleDvo, position: Int, totalSize: Int) {
        vTitle.text = data.title
        data.catIcon.roundedCorners(16).into(vCatIcon)
        itemView.setOnClickListener { onClick(data) }
    }

}