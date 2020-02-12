package com.kay.samplearch.presentation.extensions

import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.kay.samplearch.R
import com.kay.samplearch.presentation.base.BaseAdapter
import com.kay.samplearch.presentation.base.adapter.base.BaseCompositeDelegateAdapter

fun View.visible() = visibility == View.VISIBLE

fun View.visible(isVisible: Boolean, visibilityType: Int = View.GONE) {
    visibility = if (isVisible) View.VISIBLE else visibilityType
}

inline fun inAnimationTransaction(view: View, block: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && view.parent is ViewGroup) {
        val autoTransition = AutoTransition()
        autoTransition.duration = 200
        TransitionManager.beginDelayedTransition(view.parent as ViewGroup, autoTransition)
    }
    block()
}

fun View.setOnGlobalLayoutListener(callback: () -> Unit) {
    this.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            callback()
        }
    })
}

fun TextView.textPost(text: String) {
    post {
        this.text = text
    }
}

fun ViewGroup.forEach(block: (index: Int, child: View) -> Unit) {
    for (i in 0 until this.childCount) {
        block(i, getChildAt(i))
    }
}

fun ViewGroup.findOnEach(block: (index: Int, child: View) -> View?): View? {
    for (i in 0 until this.childCount) {
        val item = block(i, getChildAt(i))
        if (item != null) {
            return item
        }
    }

    return null
}

fun ViewGroup.inflateChild(restId: Int, view: (View) -> Unit = {}) {
    LayoutInflater.from(context).inflate(restId, this, true).apply {
        view(this)
    }
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, root: ViewGroup = this, attachToRoot: Boolean = false): View
        = LayoutInflater.from(this.context).inflate(layoutRes, root, attachToRoot)

fun AttributeSet?.obtainStyledAttributes(context: Context, styleable: IntArray, block: (TypedArray) -> Unit){
    if (this == null) {
        return
    }

    val typedArray = context.obtainStyledAttributes(this, styleable, 0, 0)
    try {
        block(typedArray)
    } finally {
        typedArray.recycle()
    }
}

fun ViewGroup.children(): List<View> {
    return mutableListOf<View>().apply {
        for (i in 0 until childCount) {
            add(getChildAt(i))
        }
    }
}

fun View.addRipple() = with(TypedValue()) {
    context.theme.resolveAttribute(R.attr.selectableItemBackground, this, true)
    setBackgroundResource(resourceId)
}

fun <T> RecyclerView.setItems(items: List<T>) {
    if (adapter !is BaseAdapter<*>) {
        return
    }

    (adapter as BaseAdapter<T>).setItems(items)
}

fun <T> RecyclerView.swapData(items: List<T>) {
    if (adapter !is BaseCompositeDelegateAdapter<*>) {
        return
    }

    (adapter as BaseCompositeDelegateAdapter<T>).swapData(items)
}