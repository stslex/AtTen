package com.stslex.atten.feature.home.ui.store

import com.stslex.atten.core.core.coroutine.dispatcher.AppDispatcher
import com.stslex.atten.core.paging.states.PagerAction
import com.stslex.atten.core.paging.states.PagerLoadState
import com.stslex.atten.core.paging.states.pagingMap
import com.stslex.atten.core.paging.states.toUi
import com.stslex.atten.core.ui.kit.mvi.Store
import com.stslex.atten.core.ui.kit.mvi.StoreComponent.Event.Snackbar.Error
import com.stslex.atten.feature.home.domain.interactor.HomeScreenInteractor
import com.stslex.atten.feature.home.domain.model.CreateTodoDomainModel
import com.stslex.atten.feature.home.navigation.HomeRouter
import com.stslex.atten.feature.home.ui.model.toUi
import com.stslex.atten.feature.home.ui.store.HomeStoreComponent.Action
import com.stslex.atten.feature.home.ui.store.HomeStoreComponent.Event
import com.stslex.atten.feature.home.ui.store.HomeStoreComponent.Navigation
import com.stslex.atten.feature.home.ui.store.HomeStoreComponent.State
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class HomeStore(
    private val interactor: HomeScreenInteractor,
    private val appDispatcher: AppDispatcher,
    router: HomeRouter,
) : Store<State, Event, Action, Navigation>(
    appDispatcher = appDispatcher,
    initialState = State.INIT,
    router = router
) {

    override fun process(action: Action) {
        when (action) {
            is Action.Init -> actionInit()
            is Action.LoadMore -> actionLoadMore()
            is Action.OnItemClicked -> actionOnItemClicked(action)
            is Action.Refresh -> actionRefresh()
            is Action.Retry -> actionRetry()
            is Action.OnCreateItemClicked -> actionOnCreateItemClicked()
            is Action.OnDeleteItemsClicked -> actionOnDeleteItemClicked()
            is Action.OnSelectItemClicked -> actionOnSelectItemClicked(action)
            is Action.OnBackPressed -> actionOnBackPressed()
        }
    }

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
        interactor.pagingState
            .map { pagingState ->
                pagingState.pagingMap { it.toUi() }
            }
            .distinctUntilChanged()
            .launch { pagerState ->
                updateState { currentState ->
                    val pagingUiState = pagerState.toUi(currentState.paging.config)
                    currentState.copy(
                        paging = pagingUiState.copy(
                            items = pagingUiState.items
                                .map { item ->
                                    item.copy(
                                        isSelected = currentState.selectedItems.contains(item.uuid)
                                    )
                                }
                                .toImmutableList()
                        )
                    )
                }
            }

        interactor.pagingLoadState.launch { loadState ->
            updateState { currentState ->
                currentState.copy(
                    screen = loadState.toUi()
                )
            }
        }

        interactor.pagingEvents.launch {
            sendEvent(
                Event.Snackbar(Error("error load matches"))
            )
        }

        state
            .map { it.selectedItems }
            .distinctUntilChanged()
            .launch { selectedItems ->
                updateState { currentState ->
                    currentState.copy(
                        paging = currentState.paging.copy(
                            items = currentState.paging.items
                                .map { item ->
                                    item.copy(isSelected = selectedItems.contains(item.uuid))
                                }
                                .toImmutableList()
                        )
                    )
                }
            }

        state
            .map { it.query }
            .distinctUntilChanged()
            .launch(
                onError = ::showError
            ) {
                if (interactor.pagingLoadState.value is PagerLoadState.Initial) {
                    interactor.process(PagerAction.Initial)
                } else {
                    interactor.process(PagerAction.Load(isForce = false))
                }
            }
    }

    private fun actionLoadMore() {
        interactor.process(PagerAction.Load(isForce = false))
    }

    private fun actionRefresh() {
        interactor.process(PagerAction.Refresh(isForce = true))
    }

    private fun actionRetry() {
        interactor.process(PagerAction.Retry)
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