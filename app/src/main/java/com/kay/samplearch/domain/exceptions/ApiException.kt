package com.kay.samplearch.domain.exceptions

import com.kay.samplearch.common.AppException

open class ApiException(
    val code: String?,
    override val message: String?
) : AppException() {

    /**
     * Flag that determine need to use base exception handler logic
     */
    var skipBaseHandling = false
}
