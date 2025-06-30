package com.stslex.atten.feature.details.ui.mvi

import com.stslex.atten.core.ui.mvi.handler.HandlerStore
import com.stslex.atten.feature.details.ui.mvi.DetailsStore.Action
import com.stslex.atten.feature.details.ui.mvi.DetailsStore.Event
import com.stslex.atten.feature.details.ui.mvi.DetailsStore.State

interface DetailsHandlerStore : HandlerStore<State, Action, Event>, DetailsStore