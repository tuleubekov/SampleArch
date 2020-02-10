package com.kay.samplearch.common.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

fun <T> MutableLiveData<T>.observe(lifecycleOwner: LifecycleOwner, callback: (T) -> Unit){
    observe(lifecycleOwner, Observer { callback(it ?: return@Observer) })
}

fun <T> MutableLiveData<T>.def(defaultState: T): MutableLiveData<T> {
    this.value = defaultState
    return this
}

var <T> MutableLiveData<T>.post: T?
    get() = value
    set(value) {
        postValue(value)
    }