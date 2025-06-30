package com.stslex.atten.feature.details.ui.mvi.handlers

import com.stslex.atten.core.core.logger.Log
import com.stslex.atten.core.ui.mvi.CommonEvents.Snackbar
import com.stslex.atten.feature.details.di.DetailsScope
import com.stslex.atten.feature.details.domain.interactor.DetailsInteractor
import com.stslex.atten.feature.details.ui.model.toDomain
import com.stslex.atten.feature.details.ui.model.toUi
import com.stslex.atten.feature.details.ui.mvi.DetailsHandlerStore
import com.stslex.atten.feature.details.ui.mvi.DetailsStore.Action
import com.stslex.atten.feature.details.ui.mvi.DetailsStore.Event
import com.stslex.atten.feature.details.ui.mvi.ScreenState
import com.stslex.atten.core.ui.mvi.handler.Handler
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Scoped

@Factory
@Scope(DetailsScope::class)
@Scoped
internal class CommonHandler(
    private val interactor: DetailsInteractor
) : Handler<Action.Common, DetailsHandlerStore> {

    override fun DetailsHandlerStore.invoke(action: Action.Common) {
        when (action) {
            is Action.Common.InitialLoad -> actionInit(action)
            is Action.Common.OnScreenLeft -> actionOnScreenLeft()
            is Action.Common.OnError -> actionError(action)
        }
    }

    private fun DetailsHandlerStore.actionInit(action: Action.Common.InitialLoad) {
        launch(
            action = {
                interactor.getItem(action.uuid) ?: throw IllegalStateException("Item not found")
            },
            onSuccess = { item ->
                updateState { currentState ->
                    currentState.copy(
                        item = item.toUi(),
                        createdAt = item.createdAt
                    )
                }
            },
            onError = { error -> consume(Action.Common.OnError(error)) }
        )
    }


    private fun DetailsHandlerStore.actionOnScreenLeft() {
        launch(
            action = {
                state.value.let { currentState ->
                    if (
                        state.value.item.title.isEmpty() &&
                        state.value.item.description.isEmpty()
                    ) {
                        interactor.removeItem(state.value.item.uuid)
                    } else {
                        val item = currentState.item.toDomain(currentState.createdAt)
                            ?: throw IllegalStateException("current created at is invalid")
                        interactor.updateItem(item)
                    }
                }
            },
            onSuccess = { Log.d("Item updated") },
            onError = { error -> consume(Action.Common.OnError(error)) }
        )
    }


    private fun DetailsHandlerStore.actionError(action: Action.Common.OnError) {
        val message = action.error.message ?: "Unknown error"
        // todo: implement
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