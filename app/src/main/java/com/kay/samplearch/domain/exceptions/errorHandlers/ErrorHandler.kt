package com.kay.samplearch.domain.exceptions.errorHandlers

import android.content.Context
import com.kay.samplearch.domain.exceptions.NoInternetException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

interface ErrorHandler {

    fun handleError(throwable: Throwable): Throwable
}

class GeneralErrorHandler(val context: Context) : ErrorHandler {

    override fun handleError(throwable: Throwable): Throwable {
        if (throwable is UnknownHostException || throwable is SocketTimeoutException || throwable is ConnectException) {
            return NoInternetException(context)
        }

        return throwable
    }
}