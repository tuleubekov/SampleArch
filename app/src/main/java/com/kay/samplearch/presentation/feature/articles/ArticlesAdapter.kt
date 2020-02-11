package com.kay.samplearch.presentation.feature.articles

import android.view.ViewGroup
import com.kay.samplearch.R
import com.kay.samplearch.data.models.ArticleModel
import com.kay.samplearch.presentation.base.BaseAdapter
import kotlinx.android.synthetic.main.item_article.*

class ArticlesAdapter(
    val onClick: (ArticleModel) -> Unit
) : BaseAdapter<ArticleModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<ArticleModel> {
        return ArticleViewHolder(parent, onClick)
    }
}

class ArticleViewHolder(
    viewGroup: ViewGroup,
    val onClick: (ArticleModel) -> Unit
) : BaseAdapter.ViewHolder<ArticleModel>(R.layout.item_article, viewGroup) {

    private val vTitle get() = item_article_title
    private val vCatIcon get() = item_article_cat_icon

    override fun show(data: ArticleModel, position: Int, totalSize: Int) {
        vTitle.text = data.title
        vCatIcon.setBackgroundResource(R.drawable.ic_arrow_back)
        itemView.setOnClickListener { onClick(data) }
    }

}