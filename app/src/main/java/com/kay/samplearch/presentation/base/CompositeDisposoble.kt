package com.kay.samplearch.presentation.base

import io.reactivex.disposables.Disposable
import java.util.HashMap

interface CompositeDisposable : Disposable {
    val composite: io.reactivex.disposables.CompositeDisposable
    val namedComposites : MutableMap<String, Disposable>

    fun Disposable.bind() = composite.add(this)
    fun Disposable.bind(name: String){
        val old = namedComposites[name]
        if (old != null) {
            composite.remove(old)
            old.dispose()
        }

        namedComposites[name] = this
        composite.add(this)
    }

    fun dispose(name: String) {
        val old = namedComposites[name]
        if (old != null) {
            composite.remove(old)
            old.dispose()
        }
    }

    override fun dispose() {
        namedComposites.clear()
        composite.dispose()
        composite.clear()
    }

    fun bind(it: Disposable){
        composite.add(it)
    }

    fun bind(name: String, it: Disposable){
        val old = namedComposites[name]
        if (old != null) {
            composite.remove(old)
            old.dispose()
        }

        namedComposites[name] = it
        composite.add(it)
    }

    fun containName(name: String): Boolean = namedComposites[name] != null

    override fun isDisposed() = composite.isDisposed
}

class CompositeDisposableImpl : CompositeDisposable {
    override val namedComposites = HashMap<String, Disposable>()
    override var composite = io.reactivex.disposables.CompositeDisposable()
}