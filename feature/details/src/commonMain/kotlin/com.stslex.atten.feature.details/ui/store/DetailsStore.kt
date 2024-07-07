package com.stslex.atten.feature.details.ui.store

import com.stslex.atten.core.Logger
import com.stslex.atten.core.coroutine.dispatcher.AppDispatcher
import com.stslex.atten.core.ui.mvi.Store
import com.stslex.atten.core.ui.mvi.StoreComponent.Event.Snackbar
import com.stslex.atten.feature.details.domain.interactor.DetailsInteractor
import com.stslex.atten.feature.details.navigation.DetailsRouter
import com.stslex.atten.feature.details.ui.model.toDomain
import com.stslex.atten.feature.details.ui.model.toUi
import com.stslex.atten.feature.details.ui.store.DetailsStoreComponent.Action
import com.stslex.atten.feature.details.ui.store.DetailsStoreComponent.Event
import com.stslex.atten.feature.details.ui.store.DetailsStoreComponent.Navigation
import com.stslex.atten.feature.details.ui.store.DetailsStoreComponent.State
import kotlinx.coroutines.withContext

class DetailsStore(
    private val interactor: DetailsInteractor,
    private val appDispatcher: AppDispatcher,
    router: DetailsRouter
) : Store<State, Event, Action, Navigation>(
    router = router,
    initialState = State.INIT,
    appDispatcher = appDispatcher
) {

    override fun process(action: Action) {
        when (action) {
            is Action.Init -> actionInit(action)
            Action.OnBackClicked -> actionOnBackClicked()
            is Action.OnDescriptionValueChanged -> actionOnDescriptionValueChanged(action)
            is Action.OnTitleValueChanged -> actionOnTitleValueChanged(action)
            is Action.OnScreenLeft -> actionOnScreenLeft()
            is Action.OnSaveClicked -> actionOnSaveClicked()
        }
    }

    private fun actionOnSaveClicked() {
        launch(
            action = {
                state.value.let { currentState ->
                    val item = currentState.item.toDomain(currentState.createdAt)
                        ?: throw IllegalStateException("current created at is invalid")
                    interactor.updateItem(item)
                }
            },
            onSuccess = {
                withContext(appDispatcher.main.immediate) {
                    consumeNavigation(Navigation.Back)
                }
            },
            onError = { error ->
                showError(error)
            }
        )
    }

    private fun actionInit(action: Action.Init) {
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
            onError = { error ->
                showError(error)
            }
        )
    }

    private fun actionOnScreenLeft() {
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
            onSuccess = {
                Logger.d("Item updated")
            },
            onError = { error ->
                showError(error)
            }
        )
    }

    private fun actionOnBackClicked() {
        if (
            state.value.item.title.isEmpty() &&
            state.value.item.description.isEmpty()
        ) {
            launch(
                action = {
                    interactor.removeItem(state.value.item.uuid)
                },
                onSuccess = {
                    withContext(appDispatcher.main.immediate) {
                        consumeNavigation(Navigation.Back)
                    }
                },
                onError = { error ->
                    showError(error)
                }
            )
        } else {
            consumeNavigation(Navigation.Back)
        }
    }

    private fun actionOnDescriptionValueChanged(action: Action.OnDescriptionValueChanged) {
        updateState { currentState ->
            currentState.copy(
                item = currentState.item.copy(
                    description = action.description
                )
            )
        }
    }

    private fun actionOnTitleValueChanged(action: Action.OnTitleValueChanged) {
        updateState { currentState ->
            currentState.copy(
                item = currentState.item.copy(
                    title = action.title.take(MAX_TITLE_LENGTH)
                )
            )
        }
    }

    private fun showError(error: Throwable) {
        val message = error.message ?: "Unknown error"
        // todo: implement
        if (state.value.screen is ScreenState.Content) {
            sendEvent(
                Event.Snackbar(Snackbar.Error(message))
            )
        } else {
            updateState { currentState ->
                currentState.copy(
                    screen = ScreenState.Error(message)
                )
            }
        }
    }

    companion object {
        private const val MAX_TITLE_LENGTH = 50
    }
}