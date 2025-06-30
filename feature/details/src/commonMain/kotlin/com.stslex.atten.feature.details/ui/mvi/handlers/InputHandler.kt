package com.stslex.atten.feature.details.ui.mvi.handlers

import com.stslex.atten.feature.details.di.DetailsScope
import com.stslex.atten.feature.details.ui.mvi.DetailsHandlerStore
import com.stslex.atten.feature.details.ui.mvi.DetailsStore.Action.Input
import com.stslex.atten.core.ui.mvi.handler.Handler
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Scoped

@Factory
@Scope(DetailsScope::class)
@Scoped
internal class InputHandler : Handler<Input, DetailsHandlerStore> {

    override fun DetailsHandlerStore.invoke(action: Input) {
        when (action) {
            is Input.Description -> actionOnDescriptionValueChanged(action)
            is Input.Title -> actionOnTitleValueChanged(action)
        }
    }

    private fun DetailsHandlerStore.actionOnDescriptionValueChanged(action: Input.Description) {
        updateState { currentState ->
            currentState.copy(
                item = currentState.item.copy(
                    description = action.description
                )
            )
        }
    }

    private fun DetailsHandlerStore.actionOnTitleValueChanged(action: Input.Title) {
        updateState { currentState ->
            currentState.copy(
                item = currentState.item.copy(
                    title = action.title.take(MAX_TITLE_LENGTH)
                )
            )
        }
    }

    companion object {

        private const val MAX_TITLE_LENGTH = 50
    }
}