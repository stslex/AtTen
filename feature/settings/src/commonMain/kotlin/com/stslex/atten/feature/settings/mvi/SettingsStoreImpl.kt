package com.stslex.atten.feature.settings.mvi

import com.stslex.atten.feature.settings.di.SettingsScope
import com.stslex.atten.feature.settings.mvi.SettingsStore.Action
import com.stslex.atten.feature.settings.mvi.SettingsStore.Event
import com.stslex.atten.feature.settings.mvi.SettingsStore.State
import com.stslex.atten.feature.settings.mvi.handlers.ClickHandler
import com.stslex.atten.feature.settings.mvi.handlers.LifecycleHandler
import com.stslex.atten.core.core.coroutine.dispatcher.AppDispatcher
import com.stslex.atten.core.ui.mvi.BaseStore
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.Qualifier
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Scoped

@KoinViewModel
@Scope(SettingsScope::class)
@Scoped
@Qualifier(SettingsScope::class)
class SettingsStoreImpl(
    @InjectedParam component: SettingsComponent,
    appDispatcher: AppDispatcher,
    clickHandler: ClickHandler,
    lifecycleHandler: LifecycleHandler,
) : SettingsHandlerStore, BaseStore<State, Action, Event, SettingsHandlerStore>(
    name = "SETTINGS",
    initialState = State.INIT,
    appDispatcher = appDispatcher,
    handlerCreator = { action ->
        when (action) {
            is Action.Click -> clickHandler
            is Action.Lifecycle -> lifecycleHandler
            is Action.Navigation -> component
        }
    },
    disposeActions = listOf(Action.Lifecycle.Dispose),
    initialActions = listOf(Action.Lifecycle.Init),
)