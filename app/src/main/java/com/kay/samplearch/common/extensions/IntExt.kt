package com.kay.samplearch.common.extensions

import android.content.Context
import com.kay.samplearch.di.Injector

/**
 * Convert dp value to px
 */
fun Int.toPx(context: Context): Int {
    val resources = context.resources
    val metrics = resources.displayMetrics
    return (this * (metrics.densityDpi.toFloat() / 160.0f)).toInt()
}

/**
 * Convert dp value to px
 */
fun Float.toPx(context: Context): Float {
    val resources = context.resources
    val metrics = resources.displayMetrics
    return (this * (metrics.densityDpi.toFloat() / 160.0f))
}

fun Int.toStringResource(): String {
    return Any().Injector.context.getString(this)
}