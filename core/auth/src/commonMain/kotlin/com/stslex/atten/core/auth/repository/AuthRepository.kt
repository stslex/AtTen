package com.stslex.atten.core.auth.repository

import com.stslex.atten.core.auth.repository.model.TokenDataModel
import com.stslex.atten.core.core.result.AppResult

interface AuthRepository {

    suspend fun authGoogle(googleToken: String): AppResult<TokenDataModel>

    suspend fun requestAuthGithub(): AppResult<String>

    suspend fun authGithub(code: String): AppResult<TokenDataModel>
}
