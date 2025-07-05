package com.stslex.atten.core.network.error_handler

import com.stslex.atten.core.network.model.NetworkError.ErrorRefresh
import io.ktor.client.HttpClient
import io.ktor.client.plugins.CallRequestExceptionHandler
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.HttpRequest
import io.ktor.http.HttpStatusCode

internal object RefreshTokenValidator : CallRequestExceptionHandler {

    fun HttpClient.setupResponseValidator(): HttpClient = config {
        HttpResponseValidator {
            handleResponseExceptionWithRequest(this@RefreshTokenValidator)
        }
    }

    override suspend fun invoke(cause: Throwable, request: HttpRequest) {
        throw when {
            cause !is ResponseException -> cause
            cause.response.status.value == HttpStatusCode.Unauthorized.value -> ErrorRefresh(cause)
            else -> cause
        }
    }
}

