package com.stslex.atten.feature.settings.mvi.handlers

import com.stslex.atten.feature.settings.di.SettingsScope
import com.stslex.atten.feature.settings.mvi.SettingsHandlerStore
import com.stslex.atten.feature.settings.mvi.SettingsStore.Action
import com.stslex.atten.core.ui.mvi.handler.Handler
import kotlinx.coroutines.delay
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Scoped

@Factory
@Scope(SettingsScope::class)
@Scoped()
class LifecycleHandler : Handler<Action.Lifecycle, SettingsHandlerStore> {

    override fun SettingsHandlerStore.invoke(action: Action.Lifecycle) {
        when (action) {
            Action.Lifecycle.Init -> actionInit()
            Action.Lifecycle.Dispose -> actionDispose()
        }
    }

    private fun SettingsHandlerStore.actionInit() {
        // Handle initialization logic
        updateState { currentState ->
            currentState.copy(
                username = "loading..." // Initial state before fetching username
            )
        }
        launch {
            delay(10_000)
            updateState { currentState ->
                currentState.copy(
                    username = "User123" // Simulate fetching username
                )
            }
        }
    }

    private fun SettingsHandlerStore.actionDispose() {
        // Handle disposal logic
    }
}