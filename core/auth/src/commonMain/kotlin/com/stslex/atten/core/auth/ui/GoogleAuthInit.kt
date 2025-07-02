package com.stslex.atten.core.auth.ui

import androidx.compose.runtime.Composable
import com.stslex.atten.core.auth.state.GoogleAuthState

@Composable
fun GoogleAuthInit(
    state: GoogleAuthState,
) {
    GoogleAuthPlatformInit(state)
}

@Composable
internal expect fun GoogleAuthPlatformInit(
    state: GoogleAuthState,
)