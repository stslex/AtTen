package com.stslex.atten.core.ui.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stslex.atten.core.coroutine.dispatcher.AppDispatcher
import com.stslex.atten.core.coroutine.scope.AppCoroutineScope
import com.stslex.atten.core.coroutine.scope.AppCoroutineScopeImpl
import com.stslex.atten.core.ui.mvi.StoreComponent.Action
import com.stslex.atten.core.ui.mvi.StoreComponent.Event
import com.stslex.atten.core.ui.mvi.StoreComponent.Navigation
import com.stslex.atten.core.ui.mvi.StoreComponent.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class Store<S : State, E : Event, A : Action, N : Navigation>(
    private val router: Router<N>,
    appDispatcher: AppDispatcher,
    initialState: S
) : ViewModel(), StoreAbstraction<S, E, A> {

    private val _event: MutableSharedFlow<E> = MutableSharedFlow()
    override val event: SharedFlow<E> = _event.asSharedFlow()

    private val _state: MutableStateFlow<S> = MutableStateFlow(initialState)
    override val state: StateFlow<S> = _state.asStateFlow()

    private val scope: AppCoroutineScope = AppCoroutineScopeImpl(
        scope = viewModelScope,
        appDispatcher = appDispatcher
    )

    private var _lastAction: A? = null
    protected val lastAction: A?
        get() = _lastAction

    override fun sendAction(action: A) {
        if (lastAction != action && action !is Action.RepeatLastAction) {
            _lastAction = action
        }
        process(action)
    }

    /** Process the action. This method should be overridden in the child class.*/
    protected abstract fun process(action: A)

    /**
     * Updates the state of the screen.
     * @param update - function that updates the state
     * */
    protected fun updateState(update: (S) -> S) {
        _state.update(update)
    }

    /**
     * Sends an event to the screen. The event is sent on the default dispatcher of the AppDispatcher.
     * @param event - event to be sent
     * @see AppDispatcher
     * */
    protected fun sendEvent(event: E) {
        scope.launch {
            this@Store._event.emit(event)
        }
    }

    /**
     * Navigates to the specified screen. The router is called with the specified event.
     * @param event - event to be passed to the router
     * @see Router
     * */
    protected fun consumeNavigation(event: N) {
        router(event)
    }

    /**
     * Launches a coroutine and catches exceptions. The coroutine is launched on the default dispatcher of the AppDispatcher.
     * @param onError - error handler
     * @param onSuccess - success handler
     * @param action - action to be executed
     * @return Job
     * @see Job
     * @see AppDispatcher
     * */
    protected fun <T> launch(
        onError: suspend (Throwable) -> Unit = {},
        onSuccess: suspend CoroutineScope.(T) -> Unit = {},
        action: suspend CoroutineScope.() -> T,
    ): Job = scope.launch(
        onError = onError,
        onSuccess = onSuccess,
        action = action
    )

    /**
     * Launches a flow and collects it in the screenModelScope. The flow is collected on the default dispatcher. of the AppDispatcher.
     * @param onError - error handler
     * @param each - action for each element of the flow
     * @return Job
     * @see Flow
     * @see Job
     * @see AppDispatcher
     * */
    protected fun <T> Flow<T>.launch(
        onError: suspend (cause: Throwable) -> Unit = {},
        each: suspend (T) -> Unit
    ): Job = scope.launch(
        flow = this,
        onError = onError,
        each = each,
    )
}