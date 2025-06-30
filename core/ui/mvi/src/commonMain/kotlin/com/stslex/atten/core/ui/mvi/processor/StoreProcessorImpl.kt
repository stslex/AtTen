package com.stslex.atten.core.ui.mvi.processor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import com.stslex.atten.core.ui.mvi.Store
import com.stslex.wizard.core.ui.mvi.v2.processor.ActionProcessor
import com.stslex.wizard.core.ui.mvi.v2.processor.EffectsProcessor
import kotlinx.coroutines.CoroutineScope

@Immutable
class StoreProcessorImpl<
        S : Store.State,
        A : Store.Action,
        E : Store.Event,
        TStore : Store<S, A, E>,
        >(
    private val actionProcessor: ActionProcessor<A, TStore>,
    private val eventProcessor: EffectsProcessor<E, TStore>,
    override val state: State<S>,
) : StoreProcessor<S, A, E> {

    override fun consume(action: A) {
        actionProcessor(action)
    }

    @Composable
    override fun handle(block: suspend CoroutineScope.(E) -> Unit) {
        eventProcessor(block)
    }
}