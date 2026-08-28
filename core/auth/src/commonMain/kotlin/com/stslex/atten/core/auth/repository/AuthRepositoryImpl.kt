package com.stslex.atten.core.auth.repository

import com.stslex.atten.core.auth.repository.model.TokenDataMapper
import com.stslex.atten.core.auth.repository.model.TokenDataModel
import com.stslex.atten.core.core.result.AppResult
import com.stslex.atten.core.core.result.ResultUtils.suspendRunCatching
import com.stslex.atten.core.network.api.AuthApiClient
import org.koin.core.annotation.Single

@Single
internal class AuthRepositoryImpl(
    private val authClient: AuthApiClient
) : AuthRepository {

    override suspend fun authGoogle(
        googleToken: String
    ): AppResult<TokenDataModel> = suspendRunCatching(TokenDataMapper) {
        authClient.googleAuth(googleToken)
    }

    override suspend fun requestAuthGithub(): AppResult<String> = suspendRunCatching {
        authClient.githubRequestAuth().accessToken
    }

    override suspend fun authGithub(
        code: String
    ): AppResult<TokenDataModel> = suspendRunCatching(TokenDataMapper) {
        authClient.githubAuth(code)
    }
}