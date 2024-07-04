package com.stslex.atten.feature.home.ui.store

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.stslex.atten.core.coroutine.dispatcher.AppDispatcher
import com.stslex.atten.core.ui.mvi.Store
import com.stslex.atten.core.ui.mvi.StoreComponent.Event.Snackbar.Error
import com.stslex.atten.feature.home.domain.interactor.HomeScreenInteractor
import com.stslex.atten.feature.home.domain.model.CreateTodoDomainModel
import com.stslex.atten.feature.home.navigation.HomeRouter
import com.stslex.atten.feature.home.ui.model.TodoUiModel
import com.stslex.atten.feature.home.ui.model.toUi
import com.stslex.atten.feature.home.ui.store.HomeStoreComponent.Action
import com.stslex.atten.feature.home.ui.store.HomeStoreComponent.Event
import com.stslex.atten.feature.home.ui.store.HomeStoreComponent.Navigation
import com.stslex.atten.feature.home.ui.store.HomeStoreComponent.State
import com.stslex.atten.feature.home.ui.use_case.TodoItemsPagingUseCase
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext

class HomeStore(
    private val interactor: HomeScreenInteractor,
    private val appDispatcher: AppDispatcher,
    private val todoItemsPagingUseCase: TodoItemsPagingUseCase,
    router: HomeRouter,
) : Store<State, Event, Action, Navigation>(
    appDispatcher = appDispatcher,
    initialState = State.default {
        todoItemsPagingUseCase
            .getItems()
            .map { pagingData ->
                pagingData.map { it.toUi() }
            }
    },
    router = router
) {

    override fun process(action: Action) {
        when (action) {
            is Action.Init -> actionInit()
            is Action.OnItemClicked -> actionOnItemClicked(action)
            is Action.Refresh -> actionRefresh()
            is Action.Retry -> actionRetry()
            is Action.OnCreateItemClicked -> actionOnCreateItemClicked()
            is Action.OnDeleteItemsClicked -> actionOnDeleteItemClicked()
            is Action.OnSelectItemClicked -> actionOnSelectItemClicked(action)
            is Action.OnBackPressed -> actionOnBackPressed()
        }
    }

    private fun items(): StateFlow<PagingData<TodoUiModel>> = todoItemsPagingUseCase
        .getItems()
        .map { pagingData -> pagingData.map { it.toUi() } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = PagingData.empty()
        )

    private fun actionOnBackPressed() {
        if (state.value.selectedItems.isNotEmpty()) {
            updateState { currentState ->
                currentState.copy(
                    selectedItems = persistentSetOf()
                )
            }
        } else {
            consumeNavigation(Navigation.Back)
        }
    }

    private fun actionInit() {
    }

    private fun actionRetry() {

    }

    private fun actionRefresh() {

    }

    private fun actionOnItemClicked(action: Action.OnItemClicked) {
        if (state.value.selectedItems.isNotEmpty()) {
            actionOnSelectItemClicked(Action.OnSelectItemClicked(action.id))
        } else {
            consumeNavigation(Navigation.NavigateToDetail(action.id))
        }
    }

    private fun actionOnCreateItemClicked() {
        launch(
            action = {
                interactor
                    .createItem(
                        CreateTodoDomainModel(
                            title = "",
                            description = ""
                        )
                    )
            },
            onSuccess = { item ->
                item?.let {
                    withContext(appDispatcher.main.immediate) {
                        consumeNavigation(Navigation.NavigateToDetail(it.uuid))
                    }
                }
            },
            onError = {
                showError(it)
            }
        )
    }

    private fun actionOnDeleteItemClicked() {
        launch(
            action = {
                interactor.deleteItems(state.value.selectedItems)
            },
            onSuccess = {
                // todo add success orientation
                updateState { currentState ->
                    currentState.copy(
                        selectedItems = persistentSetOf()
                    )
                }
            },
            onError = {
                showError(it)
            }
        )
    }

    private fun actionOnSelectItemClicked(action: Action.OnSelectItemClicked) {
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

    private fun showError(error: Throwable) {
        val message = error.message ?: "Unknown error"
        if (state.value.screen is ScreenState.Content) {
            sendEvent(
                Event.Snackbar(Error(message))
            )
        } else {
            updateState { currentState ->
                currentState.copy(
                    screen = ScreenState.Error(message)
                )
            }
        }
    }
}