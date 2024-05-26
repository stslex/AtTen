package com.stslex.atten.core.coroutine.scope

import com.stslex.atten.core.Logger
import com.stslex.atten.core.coroutine.coroutineExceptionHandler
import com.stslex.atten.core.coroutine.dispatcher.AppDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AppCoroutineScopeImpl(
    private val scope: CoroutineScope,
    private val appDispatcher: AppDispatcher
) : AppCoroutineScope {

    private fun exceptionHandler(
        onError: suspend (cause: Throwable) -> Unit = {},
    ) = CoroutineExceptionHandler { _, throwable ->
        Logger.exception(throwable)
        scope.launch(appDispatcher.default + coroutineExceptionHandler) {
            onError(throwable)
        }
    }

    override fun <T> launch(
        start: CoroutineStart,
        onError: suspend (Throwable) -> Unit,
        onSuccess: suspend CoroutineScope.(T) -> Unit,
        action: suspend CoroutineScope.() -> T
    ): Job = scope.launch(
        start = start,
        context = exceptionHandler(onError) + appDispatcher.default,
        block = {
            onSuccess(action())
        }
    )

    override fun <T> launch(
        flow: Flow<T>,
        onError: suspend (cause: Throwable) -> Unit,
        each: suspend (T) -> Unit
    ): Job = scope.launch(
        context = exceptionHandler(onError) + appDispatcher.default,
        block = {
            flow.collect(each)
        }
    )
}