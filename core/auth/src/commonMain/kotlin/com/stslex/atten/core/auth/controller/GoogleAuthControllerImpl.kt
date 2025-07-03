package com.stslex.atten.core.auth.controller

import androidx.compose.runtime.Composable
import com.stslex.atten.core.auth.callback.GoogleAuthCallback
import com.stslex.atten.core.auth.model.GoogleAuthResult
import com.stslex.atten.core.ui.kit.utils.ActivityHolder

internal expect class GoogleAuthControllerImpl(
    callback: GoogleAuthCallback,
    activityHolder: ActivityHolder
) : GoogleAuthController {

    @Composable
    override fun RegisterLauncher()

    override fun auth(block: (Result<GoogleAuthResult>) -> Unit)
}