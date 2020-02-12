package com.kay.samplearch.presentation.extensions

import android.content.Context
import androidx.core.content.ContextCompat
import kotlin.reflect.KClass

fun Context.getColorCompat(res: Int) = ContextCompat.getColor(this, res)

fun Context.getDrawableCompat(res: Int) = ContextCompat.getDrawable(this, res)

inline fun <reified T: Any> Any?.cast(cl: KClass<T>): T? {
    if (this is T){
        return this
    }

    return null
}