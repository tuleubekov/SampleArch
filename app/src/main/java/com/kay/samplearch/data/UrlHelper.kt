package com.kay.samplearch.data

import okhttp3.HttpUrl

abstract class UrlHelper {
    abstract val schema: String

    abstract val host: String

    abstract val apiHost: String

    fun getMainUrl() = HttpUrl.Builder()
        .scheme(schema)
        .host(host)
        .addPathSegments(apiHost)
        .build()
}

class TestUrlHelper : UrlHelper() {

    override val schema: String = "https"

    override val host: String = "lifehacker.ru"

    override val apiHost: String = "api/wp/v2/"
}