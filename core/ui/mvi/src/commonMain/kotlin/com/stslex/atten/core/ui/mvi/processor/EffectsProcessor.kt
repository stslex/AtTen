package com.stslex.wizard.core.ui.mvi.v2.processor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import com.stslex.atten.core.ui.mvi.Store
import kotlinx.coroutines.CoroutineScope

@Immutable
class EffectsProcessor<out E : Store.Event, TStore : Store<*, *, E>>(
    val store: TStore,
) {

    @Composable
    operator fun invoke(block: suspend CoroutineScope.(E) -> Unit) {
        LaunchedEffect(Unit) { store.event.collect { block(it) } }
    }
}