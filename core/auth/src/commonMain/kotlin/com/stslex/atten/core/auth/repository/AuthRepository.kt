package com.stslex.atten.core.auth.repository

import com.stslex.atten.core.auth.repository.model.TokenDataModel
import com.stslex.atten.core.core.result.AppResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun auth(googleToken: String): Flow<AppResult<TokenDataModel>>
}
