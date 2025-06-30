package com.stslex.atten.feature.home.ui.mvi.handlers

import com.stslex.atten.core.paging.states.PagerAction
import com.stslex.atten.core.paging.states.PagerLoadState
import com.stslex.atten.core.paging.states.pagingMap
import com.stslex.atten.core.paging.states.toUi
import com.stslex.atten.core.ui.mvi.CommonEvents
import com.stslex.atten.core.ui.mvi.handler.Handler
import com.stslex.atten.feature.home.di.HomeScope
import com.stslex.atten.feature.home.domain.interactor.HomeScreenInteractor
import com.stslex.atten.feature.home.ui.model.toUi
import com.stslex.atten.feature.home.ui.mvi.HomeHandlerStore
import com.stslex.atten.feature.home.ui.mvi.HomeStore.Action
import com.stslex.atten.feature.home.ui.mvi.HomeStore.Event
import com.stslex.atten.feature.home.ui.mvi.toUi
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Scoped

@Factory
@Scope(HomeScope::class)
@Scoped
internal class PagingHandler(
    private val interactor: HomeScreenInteractor
) : Handler<Action.Paging, HomeHandlerStore> {

    override fun HomeHandlerStore.invoke(action: Action.Paging) {
        when (action) {
            Action.Paging.Init -> actionInit()
            Action.Paging.LoadMore -> actionLoadMore()
            Action.Paging.Refresh -> actionRefresh()
            Action.Paging.Retry -> actionRetry()
        }
    }

    private fun HomeHandlerStore.actionInit() {
        sendEvent(Event.List.ScrollTop)
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
            sendEvent(Event.Snackbar(CommonEvents.Snackbar.Error("error load matches")))
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
                onError = { consume(Action.Common.ProcessError(it)) }
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
}