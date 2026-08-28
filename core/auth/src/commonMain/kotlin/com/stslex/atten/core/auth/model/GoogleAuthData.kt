package com.stslex.atten.core.auth.model

import androidx.compose.runtime.Stable

@Stable
data class GoogleAuthData(
    val serverAuthCode: String?,
    val accessToken: String?,
)

