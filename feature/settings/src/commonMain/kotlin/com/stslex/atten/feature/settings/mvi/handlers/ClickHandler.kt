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
    private val store: SettingsHandlerStore
) : Handler<Action.Click, SettingsHandlerStore>, SettingsHandlerStore by store {

    private var loginJob: Job? = null

    override fun SettingsHandlerStore.invoke(action: Action.Click) {
        when (action) {
            Action.Click.Back -> actionBack()
            Action.Click.Login -> actionLogin()
        }
    }

    private fun actionBack() {
        store.consume(Action.Navigation.NavBack)
    }

    private fun actionLogin() {
        authController.auth { result ->
            result
                .onSuccess { consumeLogin(it) }
                .onFailure { logger.e(it, "auth error") }
        }
    }

    private fun consumeLogin(googleInfo: GoogleAuthResult) {
        logger.i("consumeLogin: $googleInfo")
        val token = googleInfo.accessToken
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
            .collect(store.scope)
    }
}