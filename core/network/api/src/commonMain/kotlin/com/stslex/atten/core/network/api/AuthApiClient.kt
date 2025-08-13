package com.stslex.atten.core.network.api

import com.stslex.atten.core.network.api.model.TokenResponseModel

interface AuthApiClient {

    suspend fun auth(token: String): TokenResponseModel

    suspend fun refresh(): TokenResponseModel
}