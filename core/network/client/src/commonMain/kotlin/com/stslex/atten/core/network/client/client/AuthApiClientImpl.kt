package com.stslex.atten.core.network.client.client

import com.stslex.atten.core.network.api.AppHttpApi
import com.stslex.atten.core.network.api.AuthApiClient
import com.stslex.atten.core.network.api.model.request.AuthGoogleRequest
import com.stslex.atten.core.network.api.model.response.GithubAuthResponseModel
import com.stslex.atten.core.network.api.model.response.TokenResponseModel
import com.stslex.atten.core.network.client.error.RefreshTokenValidator.setupResponseValidator
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
internal class AuthApiClientImpl(
    private val appHttpApi: AppHttpApi,
    private val userStore: UserStore,
) : AuthApiClient {

    override suspend fun googleAuth(token: String): TokenResponseModel = appHttpApi.request {
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

    override suspend fun githubRequestAuth(): GithubAuthResponseModel = appHttpApi.requestDefault {
        get("https://github.com/login/oauth/authorize") { // todo => change to PKCE with remote server
            url {
                parameters.append("client_id", "client_id_value")
            }
        }.body()
    }

    override suspend fun githubAuth(code: String): TokenResponseModel = appHttpApi.request {
        post("$AUTH_HOST/$GITHUB_AUTH_HOST") {
            setBody(AuthGoogleRequest(code))
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
        private const val GITHUB_AUTH_HOST = "github"
    }

}