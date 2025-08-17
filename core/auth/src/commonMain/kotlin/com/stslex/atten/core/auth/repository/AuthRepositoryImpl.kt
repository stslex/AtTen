package com.stslex.atten.core.auth.repository

import com.stslex.atten.core.auth.repository.model.TokenDataMapper
import com.stslex.atten.core.auth.repository.model.TokenDataModel
import com.stslex.atten.core.core.result.AppResult
import com.stslex.atten.core.core.result.ResultUtils.flowRunCatching
import com.stslex.atten.core.network.api.AuthApiClient
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
internal class AuthRepositoryImpl(
    private val authClient: AuthApiClient
) : AuthRepository {

    override fun auth(
        googleToken: String
    ): Flow<AppResult<TokenDataModel>> = flowRunCatching(TokenDataMapper) {
        authClient.auth(googleToken)
    }
}