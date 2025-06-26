package com.stslex.wizard.core.ui.mvi.v2.processor

import androidx.compose.runtime.Immutable
import com.stslex.atten.core.ui.mvi.Store

@Immutable
class ActionProcessor<in A : Store.Action, TStore : Store<*, A, *>>(
    val store: TStore,
) {

    operator fun invoke(action: A) {
        store.consume(action)
    }
}