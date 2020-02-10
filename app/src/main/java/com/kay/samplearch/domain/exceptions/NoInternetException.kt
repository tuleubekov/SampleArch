package com.kay.samplearch.domain.exceptions

import android.content.Context
import com.kay.samplearch.R

class NoInternetException(
    val context: Context
) : ApiException(
    code = "",
    message = context.getString(R.string.common_error_noInternet)
)