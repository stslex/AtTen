package com.stslex.atten.feature.settings.mvi.handlers

import com.stslex.atten.core.auth.controller.GoogleAuthController
import com.stslex.atten.core.ui.mvi.handler.Handler
import com.stslex.atten.feature.settings.di.SettingsScope
import com.stslex.atten.feature.settings.mvi.SettingsHandlerStore
import com.stslex.atten.feature.settings.mvi.SettingsStore.Action
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Scoped

@Factory
@Scope(SettingsScope::class)
@Scoped()
class ClickHandler(
    private val authController: GoogleAuthController
) : Handler<Action.Click, SettingsHandlerStore> {

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
                .onSuccess { logger.i("success: $it") }
                .onFailure { logger.e(it, "auth error") }
        }
    }
}