package com.stslex.atten.core.network.error_handler

import com.stslex.atten.core.network.client.AppHttpClient
import com.stslex.atten.core.network.error_handler.RefreshTokenValidator.setupResponseValidator
import com.stslex.atten.core.network.model.NetworkError
import com.stslex.atten.core.network.model.TokenResponseModel
import com.stslex.atten.core.store.user.UserStore
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.HttpRequest
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.koin.core.annotation.Single
import org.koin.core.annotation.Singleton

@Single
@Singleton
internal class ErrorHandlerImpl(
    private val appClient: Lazy<AppHttpClient>,
    private val userStore: UserStore,
) : ErrorHandler {

    private var refreshJob: Job? = null

    override suspend fun invoke(cause: Throwable, request: HttpRequest) {
        when {
            cause !is ResponseException -> throw cause
            cause.response.status.value == HttpStatusCode.Unauthorized.value -> refreshToken()
            else -> throw cause
        }
    }

    private suspend fun refreshToken() {
        if (refreshJob?.isActive == true) return
        refreshJob = coroutineScope {
            launch {
                val tokenResponse = appClient.value
                    .client
                    .setupResponseValidator()
                    .get("auth/refresh") {
                        bearerAuth(userStore.refreshToken.value)
                    }
                    .body<TokenResponseModel>()
                userStore.uuid.value = tokenResponse.uuid
                userStore.refreshToken.value = tokenResponse.refreshToken
                userStore.accessToken.value = tokenResponse.refreshToken
                userStore.email.value = tokenResponse.email
                // TODO remove error throw after refresh token (move logic out from throwing)
                throw NetworkError.ErrorRepeatEnd
            }
        }
    }
}