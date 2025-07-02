package com.stslex.atten.core.auth.model

import androidx.compose.runtime.Stable

@Stable
sealed interface AuthEvent {

    @Stable
    data object Auth : AuthEvent

    @Stable
    data object Cancel : AuthEvent
}