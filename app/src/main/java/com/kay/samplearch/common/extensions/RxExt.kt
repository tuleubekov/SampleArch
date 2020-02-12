package com.kay.samplearch.common.extensions

import io.reactivex.Observable
import io.reactivex.Single

fun <T: Any> T.toSingle() = Single.just(this)
fun <T: Any> T.toObservable() = Observable.just(this)