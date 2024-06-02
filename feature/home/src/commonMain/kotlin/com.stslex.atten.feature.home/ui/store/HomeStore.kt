package com.stslex.atten.feature.home.ui.store

import com.stslex.atten.core.coroutine.dispatcher.AppDispatcher
import com.stslex.atten.core.paging.factory.PagerFactory
import com.stslex.atten.core.paging.model.toUi
import com.stslex.atten.core.paging.pager.PagerLoadState
import com.stslex.atten.core.paging.pager.StorePager
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
    pagingFactory: PagerFactory,
) : Store<State, Event, Action, Navigation>(
    appDispatcher = appDispatcher,
    initialState = State.INIT,
    router = router
) {

    private val pager: StorePager<TodoUiModel> by lazy {
        pagingFactory.create(
            request = { page, pageSize ->
                interactor.getToDoList(
                    page = page,
                    pageSize = pageSize
                )
            },
            scope = scope,
            mapper = { it.toUi() },
            config = state.value.paging.config
        )
    }

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
        }
    }

    private fun actionInit() {
        pager.state.launch { pagerState ->
            updateState { currentState ->
                val pagingUiState = pagerState.toUi(currentState.paging.config)
                currentState.copy(
                    paging = pagingUiState.copy(
                        items = pagingUiState.items
                            .map { item ->
                                item.copy(isSelected = currentState.selectedItems.contains(item.uuid))
                            }
                            .toImmutableList()
                    )
                )
            }
        }
        pager.loadState.launch { loadState ->
            updateState { currentState ->
                currentState.copy(
                    screen = loadState.toUi()
                )
            }
        }
        pager.loadEvents.launch {
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
                if (pager.loadState.value is PagerLoadState.Initial) {
                    pager.initialLoad()
                } else {
                    pager.refresh(isForceLoad = false)
                }
            }

        interactor.lastUpdatedNote
            .map { it.toUi() }
            .distinctUntilChanged()
            .launch { item ->
                pager.itemUpdate(item)
            }
    }

    private fun actionLoadMore() {
        pager.load()
    }

    private fun actionRefresh() {
        pager.refresh(isForceLoad = true)
    }

    private fun actionRetry() {
        pager.retry()
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
                pager.itemRemoved(state.value.selectedItems)
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