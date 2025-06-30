package com.stslex.atten.feature.home.ui.mvi

import com.stslex.atten.core.core.coroutine.dispatcher.AppDispatcher
import com.stslex.atten.core.ui.mvi.BaseStore
import com.stslex.atten.feature.home.di.HomeScope
import com.stslex.atten.feature.home.ui.mvi.HomeStore.Action
import com.stslex.atten.feature.home.ui.mvi.HomeStore.Event
import com.stslex.atten.feature.home.ui.mvi.HomeStore.State
import com.stslex.atten.feature.home.ui.mvi.handlers.ClickHandler
import com.stslex.atten.feature.home.ui.mvi.handlers.CommonHandler
import com.stslex.atten.feature.home.ui.mvi.handlers.PagingHandler
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.Qualifier
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Scoped

@KoinViewModel
@Scope(HomeScope::class)
@Scoped
@Qualifier(HomeScope::class)
internal class HomeStoreImpl(
    @InjectedParam component: HomeComponent,
    appDispatcher: AppDispatcher,
    clickHandler: ClickHandler,
    pagingHandler: PagingHandler,
    commonHandler: CommonHandler
) : HomeHandlerStore, BaseStore<State, Action, Event, HomeHandlerStore>(
    name = "HOME",
    initialState = State.INIT,
    appDispatcher = appDispatcher,
    handlerCreator = { action ->
        when (action) {
            is Action.Click -> clickHandler
            is Action.Navigation -> component
            is Action.Paging -> pagingHandler
            is Action.Common -> commonHandler
        }
    },
    initialActions = listOf(
        Action.Paging.Init
    )
)