package com.stslex.atten.core.network.api

import com.stslex.atten.core.network.api.model.response.GithubAuthResponseModel
import com.stslex.atten.core.network.api.model.response.TokenResponseModel

interface AuthApiClient {

    suspend fun googleAuth(token: String): TokenResponseModel

    suspend fun refresh(): TokenResponseModel

    suspend fun githubRequestAuth(): GithubAuthResponseModel

    suspend fun githubAuth(code: String): TokenResponseModel
}