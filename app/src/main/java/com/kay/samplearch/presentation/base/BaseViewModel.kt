package com.kay.samplearch.presentation.base

import android.content.Context
import android.util.Log
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.kay.samplearch.BuildConfig
import com.kay.samplearch.common.ThreadExecutors
import com.kay.samplearch.presentation.extensions.cast
import com.kay.samplearch.di.Injector
import com.kay.samplearch.domain.exceptions.ApiException
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.exceptions.CompositeException

open class BaseViewModel(
    val context: Context = Any().Injector.context,
    val threadExecutors: ThreadExecutors = Any().Injector.threadExecutors,
    private val uiExceptionHandler: UIExceptionHandler = UIExceptionHandler()
) : ViewModel() {
    protected val compositeDisposable =
        CompositeDisposableImpl()
    val activityActionBehavior =
        SingleLiveEvent<VmAction>()

    fun VmAction.invokeAction() {
        val isUiThread = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            android.os.Looper.getMainLooper().isCurrentThread
        } else {
            Thread.currentThread() === android.os.Looper.getMainLooper().thread
        }

        if (isUiThread) {
            activityActionBehavior.value = this
        } else {
            activityActionBehavior.postValue(this)
        }
    }

    protected open fun handleError(throwable: Throwable) {
        if (BuildConfig.DEBUG && throwable !is ApiException) {
            Log.e("Whoops", "", throwable)
        }

        if (uiExceptionHandler.canHandleOnUi(throwable)) {
            withActivity { uiExceptionHandler.handle(it, throwable) }
        }
    }

    override fun onCleared() {
        disposeAll()
        super.onCleared()
    }

    fun disposeAll() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    fun bind(disposable: Disposable, name: String? = null) {
        name?.let { compositeDisposable.bind(name, disposable) }
            ?: compositeDisposable.bind(disposable)
    }

    fun <T : Any> Observable<T>.bindSubscribe(
        onNext: (T) -> Unit = onNextStub,
        onError: (Throwable) -> Unit = { onErrorStub(it) },
        onComplete: () -> Unit = onCompleteStub,
        bindName: String? = null
    ) = subscribe(onNext, onError, onComplete).apply {
        if (bindName != null) compositeDisposable.bind(bindName, this) else compositeDisposable.bind(this)
    }

    fun <T : Any> Single<T>.bindSubscribe(
        onNext: (T) -> Unit = onNextStub,
        onError: (Throwable) -> Unit = { onErrorStub(it) },
        bindName: String? = null
    ) = subscribe(onNext, onError).apply {
        if (bindName != null) compositeDisposable.bind(bindName, this) else compositeDisposable.bind(this)
    }

    fun Completable.bindSubscribe(
        onNext: () -> Unit = onCompleteStub,
        onError: (Throwable) -> Unit = { onErrorStub(it) },
        bindName: String? = null
    ) = subscribe(onNext, onError).apply {
        if (bindName != null) compositeDisposable.bind(bindName, this) else compositeDisposable.bind(this)
    }

    val onNextStub: (Any) -> Unit = {}
    val onCompleteStub: () -> Unit = {}
    val onErrorStub: (Throwable) -> Unit = { throwable ->
        handleError(throwable)
    }

    fun Completable.scheduleOnApi() = subscribeOn(threadExecutors.apiThreadExecutor.scheduler).observeOn(threadExecutors.uiThreadExecutor.scheduler)
    fun <T> Single<T>.scheduleOnApi() = subscribeOn(threadExecutors.apiThreadExecutor.scheduler).observeOn(threadExecutors.uiThreadExecutor.scheduler)

    fun <T> Observable<T>.scheduleOnDb() = subscribeOn(threadExecutors.dbThreadExecutor.scheduler).observeOn(threadExecutors.uiThreadExecutor.scheduler)
    fun <T> Single<T>.scheduleOnDb() = subscribeOn(threadExecutors.dbThreadExecutor.scheduler).observeOn(threadExecutors.uiThreadExecutor.scheduler)

    fun Single<VmAction>.invoiceAction() = doOnSuccess { it.invokeAction() }

    fun withActivity(block: (AppCompatActivity) -> Unit) {
        VmActionCompat(block).invokeAction()
    }

    fun <T : Any> Single<T>.doOnErrorApi(block: (ApiException) -> Unit): Single<T> =
        doOnError {
            it.cast(CompositeException::class)?.also {
                it.exceptions.forEach {
                    it.cast(ApiException::class)?.also { it.skipBaseHandling = true }?.also(block)
                }
            }

            it.cast(ApiException::class)?.also { it.skipBaseHandling = true }?.also(block)
        }

    fun Completable.doOnErrorApi(block: (ApiException) -> Unit): Completable = doOnError {

        it.cast(CompositeException::class)?.also {
            it.exceptions.forEach {
                it.cast(ApiException::class)?.also { it.skipBaseHandling = true }?.also(block)
            }
        }

        it.cast(ApiException::class)?.also { it.skipBaseHandling = true }?.also(block)
    }

    fun BaseViewModel.getString(@StringRes res: Int): String = context.getString(res)
}