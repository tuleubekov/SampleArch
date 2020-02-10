package com.kay.samplearch.base

import androidx.appcompat.app.AppCompatActivity
import com.kay.samplearch.R
import com.kay.samplearch.common.extensions.showAlert
import com.kay.samplearch.domain.exceptions.ApiException

class UIExceptionHandler {

    fun canHandleOnUi(throwable: Throwable): Boolean {
        return when(throwable){
            is ApiException -> !throwable.skipBaseHandling
            else -> false
        }
    }

    fun handle(activity: AppCompatActivity, throwable: Throwable){
        when(throwable){
            is ApiException -> {
                activity.showAlert(if (throwable.message.isNullOrBlank()) activity.getString(R.string.common_error_any) else throwable.message!! )
            }
            else -> {
                activity.showAlert(activity.getString(R.string.common_error_any))
            }
        }
    }
}