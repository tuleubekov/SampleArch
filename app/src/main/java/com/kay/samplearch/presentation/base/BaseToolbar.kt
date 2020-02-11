package com.kay.samplearch.presentation.base

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.appbar.AppBarLayout
import com.kay.samplearch.R
import com.kay.samplearch.common.extensions.getColorCompat
import com.kay.samplearch.common.extensions.inflate
import com.kay.samplearch.common.extensions.obtainStyledAttributes
import com.kay.samplearch.common.extensions.toPx
import kotlinx.android.synthetic.main.view_base_toolbar.view.*

class BaseToolbar : AppBarLayout, BaseViewExt {
    private val vToolbar get() = id_view_base_toolbar

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    fun init(attrs: AttributeSet? = null) {
        addView(inflate(R.layout.view_base_toolbar), 0)

        var title = ""
        var titleColor = 0
        var toolbarBackground = 0
        var navIcon = 0

        elevation = 2f.toPx(context)

        attrs.obtainStyledAttributes(context, R.styleable.BaseToolbar) {
            title = it.getString(R.styleable.BaseToolbar_toolbarTitle) ?: ""
            titleColor = it.getColor(
                R.styleable.BaseToolbar_toolbarTitleColor, context.getColorCompat(
                    R.color.ui_color_text_primary
                ))
            toolbarBackground = it.getColor(
                R.styleable.BaseToolbar_toolbarBackground, context.getColorCompat(
                    R.color.ui_color_toolbar_background
                ))
            navIcon = it.getResourceId(R.styleable.BaseToolbar_toolbarNavIcon, R.drawable.ic_arrow_back)
        }

        activity?.apply {
            if (this.supportActionBar == null){
                setSupportActionBar(vToolbar)
            }
            this.supportActionBar?.apply {
                setDisplayShowTitleEnabled(false)
                setDisplayShowHomeEnabled(true)
            }
        }

        setBackgroundColor(toolbarBackground)

        vToolbar.setNavigationOnClickListener { activity?.onBackPressed() }
        vToolbar.setNavigationIcon(navIcon)
        vToolbar.setTitleTextColor(titleColor)
        vToolbar.title = title
    }
}