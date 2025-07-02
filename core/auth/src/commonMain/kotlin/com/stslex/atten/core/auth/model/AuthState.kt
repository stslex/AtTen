package com.stslex.atten.core.auth.model

import androidx.compose.runtime.Stable

@Stable
sealed interface AuthState {

    @Stable
    data object Idle : AuthState

    @Stable
    data object Loading : AuthState

    @Stable
    data class Success(
        val result: GoogleAuthResult
    ) : AuthState

    @Stable
    data class Error(
        val error: Throwable
    ) : AuthState
}