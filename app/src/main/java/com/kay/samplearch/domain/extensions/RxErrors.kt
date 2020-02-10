package com.kay.samplearch.domain.extensions

import com.kay.samplearch.domain.exceptions.errorHandlers.ErrorHandler
import io.reactivex.Completable
import io.reactivex.Single

fun <T> Single<T>.handleErrors(errorHandler: ErrorHandler): Single<T> = onErrorResumeNext { Single.error(errorHandler.handleError(it)) }

fun Completable.handleErrors(errorHandler: ErrorHandler): Completable = onErrorResumeNext { Completable.error(errorHandler.handleError(it)) }