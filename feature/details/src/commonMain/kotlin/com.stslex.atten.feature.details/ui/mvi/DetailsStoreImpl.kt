package com.stslex.atten.feature.details.ui.mvi

import com.stslex.atten.core.core.coroutine.dispatcher.AppDispatcher
import com.stslex.atten.core.ui.mvi.BaseStore
import com.stslex.atten.feature.details.di.DetailsScope
import com.stslex.atten.feature.details.ui.mvi.DetailsStore.Action
import com.stslex.atten.feature.details.ui.mvi.DetailsStore.Event
import com.stslex.atten.feature.details.ui.mvi.DetailsStore.State
import com.stslex.atten.feature.details.ui.mvi.handlers.ClickHandler
import com.stslex.atten.feature.details.ui.mvi.handlers.CommonHandler
import com.stslex.atten.feature.details.ui.mvi.handlers.InputHandler
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.Qualifier
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Scoped

@KoinViewModel
@Scope(DetailsScope::class)
@Scoped
@Qualifier(DetailsScope::class)
internal class DetailsStoreImpl(
    @InjectedParam component: DetailsComponent,
    appDispatcher: AppDispatcher,
    clickHandler: ClickHandler,
    commonHandler: CommonHandler,
    inputHandler: InputHandler
) : DetailsHandlerStore, BaseStore<State, Action, Event, DetailsHandlerStore>(
    name = "DETAILS",
    appDispatcher = appDispatcher,
    initialState = State.INIT,
    handlerCreator = { action ->
        when (action) {
            is Action.Click -> clickHandler
            is Action.Common -> commonHandler
            is Action.Input -> inputHandler
            is Action.Navigation -> component
        }
    },
    initialActions = listOf(Action.Common.InitialLoad(component.uuid)),
    disposeActions = listOf(Action.Common.OnScreenLeft)
)