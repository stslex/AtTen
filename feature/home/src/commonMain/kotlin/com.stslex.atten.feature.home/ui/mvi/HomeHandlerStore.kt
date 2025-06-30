package com.stslex.atten.feature.home.ui.mvi

import com.stslex.atten.core.ui.mvi.handler.HandlerStore
import com.stslex.atten.feature.home.ui.mvi.HomeStore.Action
import com.stslex.atten.feature.home.ui.mvi.HomeStore.Event
import com.stslex.atten.feature.home.ui.mvi.HomeStore.State

interface HomeHandlerStore : HandlerStore<State, Action, Event>, HomeStore