package com.stslex.atten.feature.settings.mvi.handlers

import com.stslex.atten.core.auth.controller.GoogleAuthController
import com.stslex.atten.core.auth.model.GoogleAuthResult
import com.stslex.atten.core.auth.repository.AuthRepository
import com.stslex.atten.core.core.result.ResultUtils.onSuccess
import com.stslex.atten.core.ui.mvi.handler.Handler
import com.stslex.atten.feature.settings.di.SettingsScope
import com.stslex.atten.feature.settings.mvi.SettingsHandlerStore
import com.stslex.atten.feature.settings.mvi.SettingsStore.Action
import kotlinx.coroutines.Job
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Scoped

@Factory
@Scope(SettingsScope::class)
@Scoped()
class ClickHandler(
    private val authController: GoogleAuthController,
    private val repository: AuthRepository,
//    private val store: SettingsHandlerStore
) : Handler<Action.Click, SettingsHandlerStore> {

    private var loginJob: Job? = null

    override fun SettingsHandlerStore.invoke(action: Action.Click) {
        when (action) {
            Action.Click.Back -> actionBack()
            Action.Click.Login -> actionLogin()
        }
    }

    private fun SettingsHandlerStore.actionBack() {
        consume(Action.Navigation.NavBack)
    }

    private fun SettingsHandlerStore.actionLogin() {
        authController.auth { result ->
            result
                .onSuccess { consumeLogin(it) }
                .onFailure { logger.e(it, "auth error") }
        }
    }

    private fun SettingsHandlerStore.consumeLogin(result: GoogleAuthResult) {
        logger.i("consumeLogin: $result")
        val token = when (result) {
            GoogleAuthResult.Cancelled -> {
                logger.i("Google auth cancelled by user")
                return
            }

            is GoogleAuthResult.Success -> result.data.accessToken.also {
                if (it.isNullOrBlank()) {
                    logger.e("Access token is null or empty")
                    return
                }
            }
        }

        if (token.isNullOrEmpty()) {
            logger.e(message = "Access token is null or empty")
            // todo handle error, show message to user
            return
        }
        loginJob?.cancel()
        loginJob = repository.auth(token)
            .onSuccess { logger.i("login success: $it") }
            .onError { logger.e(it, "login error") }
            .onLoading { logger.i("login loading...") }
            .collect(scope)
    }
}