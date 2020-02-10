package com.kay.samplearch.common

import io.reactivex.Scheduler

data class ThreadExecutors(
    val dbThreadExecutor: AppExecutor,
    val apiThreadExecutor: AppExecutor,
    val uiThreadExecutor: AppExecutor
)

interface AppExecutor {
    val scheduler: Scheduler

    class RxScheduler(override val scheduler: Scheduler) : AppExecutor
}

fun Scheduler.toAppExecutor(): AppExecutor = AppExecutor.RxScheduler(this)