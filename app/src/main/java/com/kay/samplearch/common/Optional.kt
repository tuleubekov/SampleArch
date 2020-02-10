package com.kay.samplearch.common

class Optional<T>(private val reference: T?) {
    fun isPresent() = reference != null
    fun isNotPresent() = !isPresent()
    fun get() = reference!!
    fun getOrDefault(default: T) = reference ?: default
    fun <F> get(block: (T?) -> F) = block(reference)
    fun getIfNull() = reference

    fun <F> map(block: (T) -> F): Optional<F> {
        return getIfNull()?.let { block(it) }.optional()
    }

    companion object {
        fun <T> empty() = Optional<T>(null)
        fun <T> of(t: T?) = Optional(t)
    }

}

fun <T> T?.optional(): Optional<T> = Optional(this)
fun <T> Optional<T>?.getOrDefault(default: T) = this?.getOrDefault(default) ?: default