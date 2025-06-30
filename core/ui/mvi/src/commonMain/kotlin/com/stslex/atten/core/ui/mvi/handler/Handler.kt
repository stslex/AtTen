package com.stslex.atten.core.ui.mvi.handler

import com.stslex.atten.core.ui.mvi.Store.Action

fun interface Handler<A : Action, TStore : HandlerStore<*, *, *>> {

    operator fun TStore.invoke(action: A)

}
