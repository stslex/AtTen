package com.stslex.atten.core.network.client.error

import com.stslex.atten.core.network.api.model.NetworkError
import io.ktor.client.HttpClient
import io.ktor.client.plugins.CallRequestExceptionHandler
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.HttpRequest
import io.ktor.http.HttpStatusCode.Companion.Unauthorized

internal object RefreshTokenValidator : CallRequestExceptionHandler {

    fun HttpClient.setupResponseValidator(): HttpClient = config {
        HttpResponseValidator {
            handleResponseExceptionWithRequest(this@RefreshTokenValidator)
        }
    }

    override suspend fun invoke(cause: Throwable, request: HttpRequest) {
        throw when {
            cause !is ResponseException -> cause
            cause.response.status.value == Unauthorized.value -> NetworkError.ErrorRefresh(cause)
            else -> cause
        }
    }
}