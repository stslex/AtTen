package com.stslex.atten.core.ui.mvi

import com.stslex.atten.core.ui.mvi.handler.HandlerStore
import com.stslex.atten.core.ui.mvi.Store.Action
import com.stslex.atten.core.ui.mvi.Store.Event
import com.stslex.atten.core.ui.mvi.Store.State
import com.stslex.wizard.core.ui.mvi.v2.Handler

fun <S : State, A : Action, E : Event, HStore : HandlerStore<S, A, E>> Handler<A, HStore>.invoke(
    store: HStore,
    action: A
) {
    with(store) {
        this.invoke(action)
    }
}

inline fun <S : State, A : Action, E : Event, HStore : HandlerStore<S, A, E>> handler(
    crossinline block: HandlerStore<S, A, E>.(action: A) -> Unit
) = Handler<A, HStore> { block(it) }
