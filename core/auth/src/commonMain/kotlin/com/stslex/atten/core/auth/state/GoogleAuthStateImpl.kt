package com.stslex.atten.core.auth.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import com.stslex.atten.core.auth.model.AuthEvent
import com.stslex.atten.core.auth.model.AuthState
import com.stslex.atten.core.auth.model.GoogleAuthResult
import com.stslex.atten.core.core.logger.Log
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

@Immutable
class GoogleAuthStateImpl private constructor() : GoogleAuthState {

    private val _state: MutableStateFlow<AuthState> = MutableStateFlow(AuthState.Idle)
    override val state: StateFlow<AuthState> = _state.asStateFlow()

    private val _event: MutableSharedFlow<AuthEvent> = MutableSharedFlow()
    override val event: SharedFlow<AuthEvent> = _event.asSharedFlow()

    override suspend fun consume(event: AuthEvent) {
        logger.d("consume: $event")
        _event.emit(event)
    }

    override fun consumeResult(result: Result<GoogleAuthResult>) {
        logger.d("result: $result")
        result
            .onFailure { _state.value = AuthState.Error(it) }
            .onSuccess { _state.value = AuthState.Success(it) }
    }


    companion object {

        private val logger = Log.tag("GoogleAuthState")

        @Composable
        fun rememberGoogleAuthState(): GoogleAuthState = remember { GoogleAuthStateImpl() }
    }
}