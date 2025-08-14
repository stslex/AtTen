package com.stslex.atten.core.network.client.client

import com.stslex.atten.core.network.api.AppHttpApi
import com.stslex.atten.core.network.api.AuthApiClient
import com.stslex.atten.core.network.api.model.request.AuthGoogleRequest
import com.stslex.atten.core.network.client.error.RefreshTokenValidator.setupResponseValidator
import com.stslex.atten.core.network.api.model.TokenResponseModel
import com.stslex.atten.core.store.user.UserStore
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.koin.core.annotation.Single
import org.koin.core.annotation.Singleton

@Single
@Singleton
class AuthApiClientImpl(
    private val appHttpApi: AppHttpApi,
    private val userStore: UserStore,
) : AuthApiClient {

    override suspend fun auth(token: String): TokenResponseModel = appHttpApi.request {
        post("$AUTH_HOST/$GOOGLE_AUTH_HOST") {
            setBody(AuthGoogleRequest(token))
        }
            .body<TokenResponseModel>()
            .saveIntoUserStore()
    }

    override suspend fun refresh(): TokenResponseModel = appHttpApi.request {
        setupResponseValidator()
            .get("$AUTH_HOST/$REFRESH_HOST") {
                bearerAuth(userStore.refreshToken.value)
            }
            .body<TokenResponseModel>()
            .saveIntoUserStore()
    }

    private fun TokenResponseModel.saveIntoUserStore(): TokenResponseModel = apply {
        userStore.uuid.value = uuid
        userStore.refreshToken.value = refreshToken
        userStore.accessToken.value = accessToken
        userStore.email.value = email
    }

    companion object {

        private const val AUTH_HOST = "auth"
        private const val REFRESH_HOST = "refresh"
        private const val GOOGLE_AUTH_HOST = "google"
    }

}