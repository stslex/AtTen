package com.stslex.atten.core.coroutine

import com.stslex.atten.core.logger.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    Log.e(throwable)
}

suspend fun <T, R> List<T>.asyncMap(
    transform: suspend (T) -> R
): List<R> = coroutineScope {
    map { item -> async { transform(item) } }
}.awaitAll()