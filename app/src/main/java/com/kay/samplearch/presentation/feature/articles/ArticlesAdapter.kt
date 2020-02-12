package com.kay.samplearch.presentation.feature.articles

import com.kay.samplearch.R
import com.kay.samplearch.presentation.base.adapter.KDelegateAdapter
import com.kay.samplearch.presentation.base.adapter.model.IAdapterItem
import kotlinx.android.synthetic.main.item_article.*

class ArticlesAdapter(
    val onClick: (IAdapterItem) -> Unit
) : KDelegateAdapter<ArticleDvo>() {

    override val layoutId get() = R.layout.item_article

    override fun isForViewType(items: List<*>, position: Int): Boolean = items[position] is ArticleDvo

    override fun onBind(item: ArticleDvo, viewHolder: KViewHolder) = with(viewHolder) {
        val vTitle = item_article_title
        val vCatIcon = item_article_cat_icon

        vTitle.text = item.title
        item.catIcon.roundedCorners(16).into(vCatIcon)
        itemView.setOnClickListener { onClick(item) }
    }
}