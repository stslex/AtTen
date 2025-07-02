package com.stslex.atten.core.auth.state

import com.stslex.atten.core.auth.model.AuthEvent
import com.stslex.atten.core.auth.model.AuthState
import com.stslex.atten.core.auth.model.GoogleAuthResult
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface GoogleAuthState {

    val state: StateFlow<AuthState>

    val event: SharedFlow<AuthEvent>

    suspend fun consume(event: AuthEvent)

    fun consumeResult(result: Result<GoogleAuthResult>)
}