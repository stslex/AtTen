package com.stslex.atten.core.auth.model

import androidx.compose.runtime.Stable

@Stable
data class GoogleAuthResult(
    val serverAuthCode: String?,
    val accessToken: String?,
)