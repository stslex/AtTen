package com.stslex.atten.core.ui.mvi.processor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import com.stslex.atten.core.ui.mvi.Store.Action
import com.stslex.atten.core.ui.mvi.Store.Event
import com.stslex.atten.core.ui.mvi.Store.State
import com.stslex.atten.core.ui.mvi.BaseStore
import com.stslex.atten.core.ui.navigation.Component
import com.stslex.wizard.core.ui.mvi.v2.processor.ActionProcessor
import com.stslex.wizard.core.ui.mvi.v2.processor.EffectsProcessor
import kotlinx.coroutines.CoroutineScope
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.component.KoinScopeComponent
import org.koin.core.parameter.parametersOf
import androidx.compose.runtime.State as ComposeState


/**
 * StoreProcessor is an interface that defines the contract for processing actions and events in a store.
 * It provides methods to consume actions and handle events.
 *
 * @param S The type of the state.
 * @param A The type of the action.
 * @param E The type of the event.
 */
@Immutable
interface StoreProcessor<S : State, A : Action, E : Event> {

    val state: ComposeState<S>

    fun consume(action: A)

    @Composable
    fun handle(block: suspend CoroutineScope.(E) -> Unit)
}

/**
 * StoreProcessorImpl is an implementation of the StoreProcessor interface.
 * It provides methods to consume actions and handle events in a store.
 *
 * @param S The type of the state.
 * @param A The type of the action.
 * @param E The type of the event.
 * @param TStoreImpl The type of the store implementation.
 */
@Composable
inline fun <S : State,
        A : Action,
        E : Event,
        reified TStoreImpl : BaseStore<S, A, E, *>,
        TComponent : Component
        > KoinScopeComponent.rememberStoreProcessor(component: TComponent): StoreProcessor<S, A, E> {
    val store = koinViewModel<TStoreImpl>(
        scope = scope,
        qualifier = scope.scopeQualifier,
        parameters = {
            parametersOf(component)
        }
    )
    val actionProcessor = remember { ActionProcessor(store) }
    val effectsProcessor = remember { EffectsProcessor(store) }
    val state = remember { store.state }.collectAsState()
    return remember {
        StoreProcessorImpl<S, A, E, TStoreImpl>(
            actionProcessor = actionProcessor,
            eventProcessor = effectsProcessor,
            state = state,
        )
    }
}

