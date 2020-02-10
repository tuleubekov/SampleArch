package com.kay.samplearch.base

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import io.reactivex.disposables.Disposable
import kotlin.reflect.KClass

abstract class BaseFragment : Fragment() {

    private val fragmentComposit = CompositeDisposableImpl()
    private val _viewModels by lazy { provideViewModel().mapKeys { it.key.java.name } }

    open val attachToFragmentContext = true
    protected var firstViewCreated = true

    abstract val layoutId: Int
    abstract fun provideViewModel(): Map<KClass<*>, () -> BaseViewModel>

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> vm(cl: KClass<T>? = null): T {
        val provider = cl?.let { _viewModels[it.java.name] } ?: _viewModels.values.first()

        return provider() as T
    }

    fun <T : BaseViewModel> vmCreator(cl: KClass<T>, factory: ViewModelProvider.Factory? = null): Pair<KClass<*>, () -> BaseViewModel> {
        return cl to { viewModelProvider(factory).get(cl.java) }
    }

    override fun onStart() {
        super.onStart()
        firstViewCreated = false

        observeChanges().forEach {
            if (it.second != null) {
                fragmentComposit.bind(it.second!!, it.first)
            } else {
                fragmentComposit.bind(it.first)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        fragmentComposit.dispose()
    }

    open fun observeChanges(): List<Pair<Disposable, String?>> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _viewModels.values.forEach { it ->
            it().activityActionBehavior.observe(this@BaseFragment, Observer {
                it?.invoke(activity as? AppCompatActivity ?: return@Observer)
            })
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    open fun handleActivityBackPressed(): Boolean {
        return false
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> Fragment.requiredArg(block: Bundle.() -> Any?): T {
        return arguments?.block() as? T ?: throw RuntimeException("Missing fragment required args")
    }

    private fun viewModelProvider(factory: ViewModelProvider.Factory? = null): ViewModelProvider {
        return if (attachToFragmentContext) {
            ViewModelProviders.of(this, factory)
        } else {
            ViewModelProviders.of(activity!!, factory)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Parcelable> launcher(): T {
        return arguments?.getParcelable<Parcelable>(LAUNCHER) as? T
            ?: throw RuntimeException("Launcher can not be null")
    }

    companion object {
        const val LAUNCHER = "LAUNCHER"
    }
}

fun <T : BaseFragment> T.launcher(launcher: Parcelable): T {
    arguments = arguments ?: Bundle().apply {
        putParcelable(BaseFragment.LAUNCHER, launcher)
    }

    return this
}