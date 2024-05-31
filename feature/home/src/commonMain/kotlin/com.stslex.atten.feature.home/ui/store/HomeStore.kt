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
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class HomeStore(
    private val interactor: HomeScreenInteractor,
    appDispatcher: AppDispatcher,
    router: HomeRouter,
    pagingFactory: PagerFactory,
) : Store<State, Event, Action, Navigation>(
    appDispatcher = appDispatcher,
    initialState = State.INIT,
    router = router
) {

    private val pager: StorePager<TodoUiModel> = pagingFactory.create(
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


    override fun process(action: Action) {
        when (action) {
            is Action.Init -> actionInit()
            is Action.LoadMore -> actionLoadMore()
            is Action.OnItemClicked -> actionOnItemClicked(action)
            is Action.Refresh -> actionRefresh()
            is Action.Retry -> actionRetry()
            is Action.OnCreateItemClicked -> actionOnCreateItemClicked()
            is Action.OnDeleteItemClicked -> actionOnDeleteItemClicked(action)
        }
    }

    private fun actionInit() {
        pager.state.launch { pagerState ->
            updateState { currentState ->
                currentState.copy(
                    paging = pagerState.toUi(currentState.paging.config)
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
        consumeNavigation(Navigation.NavigateToDetail(action.id))
    }

    private fun actionOnCreateItemClicked() {
        launch(
            action = {
                interactor
                    .createItem(
                        CreateTodoDomainModel(
                            title = "New item",
                            description = "New item description"
                        )
                    )
            },
            onSuccess = { item ->
                // todo add success orientation
                item?.toUi()?.let(pager::itemInserted)
            },
            onError = {
                showError(it)
            }
        )
        // todo consumeNavigation(Navigation.NavigateToCreate)
    }

    private fun actionOnDeleteItemClicked(action: Action.OnDeleteItemClicked) {
        launch(
            action = {
                interactor.deleteItem(action.id)
            },
            onSuccess = {
                // todo add success orientation
                state.value.paging.items.firstOrNull { it.id == action.id }?.let {
                    pager.itemRemoved(it.uniqueKey)
                }
            },
            onError = {
                showError(it)
            }
        )
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