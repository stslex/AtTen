package com.stslex.atten.core.coroutine.scope

import com.stslex.atten.core.coroutine.dispatcher.AppDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

interface AppCoroutineScope {

    /**
     * Launches a coroutine and catches exceptions. The coroutine is launched on the default dispatcher of the AppDispatcher.
     * @param onError - error handler
     * @param onSuccess - success handler
     * @param action - action to be executed
     * @return Job
     * @see Job
     * @see AppDispatcher
     * */
    fun <T> launch(
        start: CoroutineStart = CoroutineStart.DEFAULT,
        onError: suspend (Throwable) -> Unit = {},
        onSuccess: suspend CoroutineScope.(T) -> Unit = {},
        action: suspend CoroutineScope.() -> T,
    ): Job

    /**
     * Launches a flow and collects it in the screenModelScope. The flow is collected on the default dispatcher. of the AppDispatcher.
     * @param onError - error handler
     * @param each - action for each element of the flow
     * @return Job
     * @see Flow
     * @see Job
     * @see AppDispatcher
     * */
    fun <T> launch(
        flow: Flow<T>,
        onError: suspend (cause: Throwable) -> Unit = {},
        each: suspend (T) -> Unit
    ): Job
}

