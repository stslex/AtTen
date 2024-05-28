package com.stslex.atten.feature.home.ui.store

import com.stslex.atten.core.coroutine.dispatcher.AppDispatcher
import com.stslex.atten.core.paging.factory.PagerFactory
import com.stslex.atten.core.paging.model.toUi
import com.stslex.atten.core.paging.pager.PagerLoadState
import com.stslex.atten.core.paging.pager.StorePager
import com.stslex.atten.core.ui.mvi.Store
import com.stslex.atten.core.ui.mvi.StoreComponent.Event.Snackbar.Error
import com.stslex.atten.feature.home.domain.interactor.HomeScreenInteractor
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
    appDispatcher: AppDispatcher,
    router: HomeRouter,
    pagingFactory: PagerFactory,
    interactor: HomeScreenInteractor,
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
            Action.Init -> actionInit()
            Action.LoadMore -> actionLoadMore()
            is Action.OnItemClicked -> actionOnItemClicked(action)
            is Action.Refresh -> actionRefresh()
            is Action.Retry -> actionRetry()
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
//        TODO("Not yet implemented")
    }

    private fun actionOnItemClicked(action: Action.OnItemClicked) {
        consumeNavigation(Navigation.NavigateToDetail(action.id))
    }

    private fun actionRefresh() {

    }

    private fun actionRetry() {

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