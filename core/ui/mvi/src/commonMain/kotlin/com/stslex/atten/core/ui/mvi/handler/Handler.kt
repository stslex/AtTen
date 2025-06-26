package com.stslex.wizard.core.ui.mvi.v2

import com.stslex.atten.core.ui.mvi.handler.HandlerStore
import com.stslex.atten.core.ui.mvi.Store.Action

fun interface Handler<A : Action, TStore : HandlerStore<*, *, *>> {

    operator fun TStore.invoke(action: A)

}
