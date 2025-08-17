package com.stslex.atten.core.core.result

import com.stslex.atten.core.core.model.AppError

sealed interface AppResult<out T : Any> : AppResultMapper<T> {

    data class Success<T : Any>(val data: T) : AppResult<T> {

        override fun <R : Any> map(mapper: Mapping<T, R>): AppResult<R> = Success(mapper(data))
    }

    data class Error(val error: AppError) : AppResult<Nothing> {

        override fun <R : Any> map(mapper: Mapping<Nothing, R>): AppResult<R> = Error(error)
    }

    data object Loading : AppResult<Nothing> {

        override fun <R : Any> map(mapper: Mapping<Nothing, R>): AppResult<R> = Loading
    }

    fun onSuccess(action: (T) -> Unit): AppResult<T> = this.apply {
        (this as? Success<T>)?.let { action(it.data) }
    }

    fun onError(action: (AppError) -> Unit): AppResult<T> =
        this.apply { (this as? Error)?.let { action(it.error) } }

    fun onLoading(action: () -> Unit): AppResult<T> = this.apply {
        if (this is Loading) action()
    }

    companion object {

        fun <T : Any> success(value: T): AppResult<T> = Success(value)

        fun error(error: AppError): AppResult<*> = Error(error)

        fun loading(): AppResult<*> = Loading
    }
}
