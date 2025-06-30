package com.stslex.atten.feature.home.ui.mvi.handlers

import com.stslex.atten.core.ui.mvi.CommonEvents.Snackbar
import com.stslex.atten.feature.home.di.HomeScope
import com.stslex.atten.feature.home.ui.mvi.HomeHandlerStore
import com.stslex.atten.feature.home.ui.mvi.HomeStore.Action
import com.stslex.atten.feature.home.ui.mvi.HomeStore.Event
import com.stslex.atten.feature.home.ui.mvi.ScreenState
import com.stslex.atten.core.ui.mvi.handler.Handler
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Scoped

@Factory
@Scope(HomeScope::class)
@Scoped
internal class CommonHandler : Handler<Action.Common, HomeHandlerStore> {

    override fun HomeHandlerStore.invoke(action: Action.Common) {
        when (action) {
            is Action.Common.ProcessError -> processError(action)
        }
    }

    private fun HomeHandlerStore.processError(
        action: Action.Common.ProcessError
    ) {
        val message = action.error.message ?: "Unknown error"
        if (state.value.screen is ScreenState.Content) {
            sendEvent(Event.Snackbar(Snackbar.Error(message)))
        } else {
            updateState { currentState ->
                currentState.copy(
                    screen = ScreenState.Error(message)
                )
            }
        }
    }
}