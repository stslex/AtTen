package com.stslex.atten.core.network.client.error

import com.stslex.atten.core.core.model.AppError
import com.stslex.atten.core.network.api.AuthApiClient
import com.stslex.atten.core.network.api.ErrorHandler
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.HttpRequest
import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.koin.core.annotation.Single
import org.koin.core.annotation.Singleton

@Single
@Singleton
internal class ErrorHandlerImpl(
    private val authClient: Lazy<AuthApiClient>
) : ErrorHandler {

    private var refreshJob: Job? = null

    override suspend fun invoke(cause: Throwable, request: HttpRequest) {
        when {
            cause is ErrorRepeatEnd -> throw cause.cause
            cause !is ResponseException -> throw cause
            cause.response.status.value == Unauthorized.value -> refreshToken(cause)
            else -> throw cause
        }
    }

    private suspend fun refreshToken(cause: Throwable) {
        if (refreshJob?.isActive == true) return
        refreshJob = coroutineScope {
            launch {
                authClient.value.refresh()
                // TODO remove error throw after refresh token (move logic out from throwing)
                throw ErrorRepeatEnd(cause)
            }
        }
    }

    /**
     * Error repeat request.
     * Show that the request was repeated after a refresh token
     * @see com.stslex.atten.core.network.client.client.AppHttpApiImpl.request
     */
    private data class ErrorRepeatEnd(
        override val cause: Throwable
    ) : AppError("", cause)
}
