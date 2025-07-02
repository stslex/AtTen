package com.stslex.atten.feature.home.ui.mvi.handlers

import com.stslex.atten.core.ui.mvi.handler.Handler
import com.stslex.atten.feature.home.di.HomeScope
import com.stslex.atten.feature.home.domain.interactor.HomeScreenInteractor
import com.stslex.atten.feature.home.domain.model.CreateTodoDomainModel
import com.stslex.atten.feature.home.ui.mvi.HomeHandlerStore
import com.stslex.atten.feature.home.ui.mvi.HomeStore.Action
import com.stslex.atten.feature.home.ui.mvi.HomeStore.Event
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableSet
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Scoped

@Factory
@Scope(HomeScope::class)
@Scoped
internal class ClickHandler(
    private val interactor: HomeScreenInteractor
) : Handler<Action.Click, HomeHandlerStore> {

    override fun HomeHandlerStore.invoke(action: Action.Click) {
        when (action) {
            is Action.Click.OnBackPressed -> actionOnBackPressed()
            is Action.Click.OnCreateItemClicked -> actionOnCreateItemClicked()
            is Action.Click.OnDeleteItemsClicked -> actionOnDeleteItemClicked()
            is Action.Click.OnItemClicked -> actionOnItemClicked(action)
            is Action.Click.OnSelectItemClicked -> actionOnSelectItemClicked(action)
            is Action.Click.OnSettingsClicked -> actionSettingsClick()
        }
    }

    private fun HomeHandlerStore.actionSettingsClick() {
        consume(Action.Navigation.Settings)
    }

    private fun HomeHandlerStore.actionOnItemClicked(
        action: Action.Click.OnItemClicked
    ) {
        if (state.value.selectedItems.isNotEmpty()) {
            consume(Action.Click.OnSelectItemClicked(action.id))
        } else {
            sendEvent(Event.Haptic.Click)
            consume(Action.Navigation.NavigateToDetail(action.id))
        }
    }

    private fun HomeHandlerStore.actionOnCreateItemClicked() {
        sendEvent(Event.Haptic.Click)
        launch(
            action = {
                interactor.createItem(CreateTodoDomainModel(title = "", description = ""))
            },
            onSuccess = { item ->
                if (item == null) {
                    consume(Action.Common.ProcessError(IllegalStateException("Item is null")))
                } else {
                    consume(Action.Navigation.NavigateToDetail(item.uuid))
                }
            },
            onError = { consume(Action.Common.ProcessError(it)) }
        )
    }

    private fun HomeHandlerStore.actionOnBackPressed() {
        sendEvent(Event.Haptic.Click)
        if (state.value.selectedItems.isNotEmpty()) {
            updateState { currentState ->
                currentState.copy(
                    selectedItems = persistentSetOf()
                )
            }
        } else {
            consume(Action.Navigation.Back)
        }
    }

    private fun HomeHandlerStore.actionOnDeleteItemClicked() {
        sendEvent(Event.Haptic.Click)
        launch(
            action = {
                interactor.deleteItems(state.value.selectedItems)
            },
            onSuccess = {
                // todo add success orientation
                updateState { currentState ->
                    currentState.copy(selectedItems = persistentSetOf())
                }
            },
            onError = { consume(Action.Common.ProcessError(it)) }
        )
    }

    private fun HomeHandlerStore.actionOnSelectItemClicked(
        action: Action.Click.OnSelectItemClicked
    ) {
        sendEvent(Event.Haptic.LongClick)
        updateState { currentState ->
            val selectedItems = currentState.selectedItems.toMutableSet()
            if (selectedItems.contains(action.id)) {
                selectedItems.remove(action.id)
            } else {
                selectedItems.add(action.id)
            }
            currentState.copy(
                selectedItems = selectedItems.toImmutableSet(),
            )
        }
    }
}