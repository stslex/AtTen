package com.stslex.atten.feature.settings.domain

import com.stslex.atten.core.auth.repository.AuthRepository
import com.stslex.atten.core.auth.repository.model.TokenDataModel
import com.stslex.atten.core.core.logger.Log
import com.stslex.atten.core.core.result.AppResult
import com.stslex.atten.core.core.result.AppResult.Companion.onError
import com.stslex.atten.core.core.result.AppResult.Companion.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class AuthInteractorImpl(
    private val repository: AuthRepository,
) : SettingsInteractor {

    private val logger = Log.tag("AuthInteractor")

    // todo => add googleAuthController
    override fun authGoogle(token: String): Flow<AppResult<TokenDataModel>> = flow {
        emit(repository.authGoogle(token))
    }

    override fun authGithub(): Flow<AppResult<TokenDataModel>> = flow {
        repository.requestAuthGithub()
            .onError { emit(AppResult.error(it)) }
            .onSuccess {
                logger.v("github request: $it")
                emit(repository.authGithub(it))
            }
    }
}