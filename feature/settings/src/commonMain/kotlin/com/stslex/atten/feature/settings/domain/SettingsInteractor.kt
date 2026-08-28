package com.stslex.atten.feature.settings.domain

import com.stslex.atten.core.auth.repository.model.TokenDataModel
import com.stslex.atten.core.core.result.AppResult
import kotlinx.coroutines.flow.Flow

interface SettingsInteractor {

    fun authGoogle(token: String): Flow<AppResult<TokenDataModel>>

    fun authGithub(): Flow<AppResult<TokenDataModel>>
}
