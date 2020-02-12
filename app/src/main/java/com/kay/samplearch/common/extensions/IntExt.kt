package com.kay.samplearch.common.extensions

import android.content.Context
import com.kay.samplearch.di.Injector

fun Int.toPx(context: Context): Int {
    val resources = context.resources
    val metrics = resources.displayMetrics
    return (this * (metrics.densityDpi.toFloat() / 160.0f)).toInt()
}

fun Float.toPx(context: Context): Float {
    val resources = context.resources
    val metrics = resources.displayMetrics
    return (this * (metrics.densityDpi.toFloat() / 160.0f))
}

fun Int.toStringResource(): String {
    return Any().Injector.context.getString(this)
}