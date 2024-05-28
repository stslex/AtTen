package com.stslex.atten.feature.home.ui.store

import com.stslex.atten.core.coroutine.dispatcher.AppDispatcher
import com.stslex.atten.core.ui.mvi.Store
import com.stslex.atten.feature.home.navigation.HomeRouter
import com.stslex.atten.feature.home.ui.store.HomeStoreComponent.Action
import com.stslex.atten.feature.home.ui.store.HomeStoreComponent.Event
import com.stslex.atten.feature.home.ui.store.HomeStoreComponent.Navigation
import com.stslex.atten.feature.home.ui.store.HomeStoreComponent.State

class HomeStore(
    appDispatcher: AppDispatcher,
    router: HomeRouter
) : Store<State, Event, Action, Navigation>(
    appDispatcher = appDispatcher,
    initialState = State.INIT,
    router = router
) {

    override fun process(action: Action) {
        when (action) {
            Action.Init -> actionInit()
            Action.LoadMore -> actionLoadMore()
            is Action.OnItemClicked -> actionOnItemClicked(action)
        }
    }

    private fun actionInit() {
//        TODO("Not yet implemented")
    }

    private fun actionLoadMore() {
//        TODO("Not yet implemented")
    }

    private fun actionOnItemClicked(action: Action.OnItemClicked) {
        consumeNavigation(Navigation.NavigateToDetail(action.id))
    }
}