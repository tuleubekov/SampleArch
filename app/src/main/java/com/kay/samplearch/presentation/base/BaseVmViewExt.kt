package com.kay.samplearch.presentation.base

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlin.reflect.KClass

interface BaseVmViewExt<T: BaseViewModel>: BaseViewExt {

    val viewModelProvider: Pair<KClass<*>, () -> BaseViewModel>
    val useFragmentContext: Boolean get() = true
    val viewModelPrefixKey get() = ""

    @Suppress("UNCHECKED_CAST")
    val vm: T? get() = activity?.let {
        viewModelProvider.second() as T
    }

    val lifecycleOwner: LifecycleOwner? get() {
        return if (useFragmentContext && fragment != null){ fragment?.viewLifecycleOwner } else { activity }
    }

    fun bindVmActions(){
        vm?.activityActionBehavior?.observe(lifecycleOwner!!, Observer {
            it.invoke(activity!! as? AppCompatActivity)
        })
    }

    fun vmCreator(cl: KClass<T>, factory: ViewModelProvider.Factory? = null): Pair<KClass<T>, () -> BaseViewModel> {
        return cl to { activity?.let {
            val key = viewModelPrefixKey+cl.java.canonicalName

            if (useFragmentContext && fragment != null){
                ViewModelProviders.of(fragment!!, factory).get(key, cl.java)
            } else {
                ViewModelProviders.of(it, factory).get(key, cl.java)
            }
        } ?: throw RuntimeException("Activity not ready") }
    }
}

interface BaseViewExt {
    fun getContext(): Context

    val activity: AppCompatActivity? get() {
        var context = getContext()
        while (context is ContextWrapper) {
            if (context is AppCompatActivity) {
                return context
            }
            context = context.baseContext
        }
        return null
    }

    val fragment: Fragment? get(){
        return (activity as? BaseActivity)?.let {
            it.supportFragmentManager.findFragmentById(it.fragmentContainerId)
        }
    }
}