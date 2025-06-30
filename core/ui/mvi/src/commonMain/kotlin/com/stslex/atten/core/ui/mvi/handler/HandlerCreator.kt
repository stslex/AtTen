package com.stslex.atten.core.ui.mvi.handler

import com.stslex.atten.core.ui.mvi.Store.Action
import com.stslex.atten.core.ui.mvi.Store.Event
import com.stslex.atten.core.ui.mvi.Store.State

fun interface HandlerCreator<S : State, A : Action, E : Event, HStore : HandlerStore<S, A, E>> {

    operator fun invoke(action: A): Handler<*, HStore>
}