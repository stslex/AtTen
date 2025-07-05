package com.stslex.atten.core.auth.controller

import androidx.compose.runtime.Composable
import com.stslex.atten.core.auth.model.GoogleAuthResult

interface GoogleAuthController {

    @Composable
    fun RegisterLauncher()

    fun auth(block: (Result<GoogleAuthResult>) -> Unit)
}

