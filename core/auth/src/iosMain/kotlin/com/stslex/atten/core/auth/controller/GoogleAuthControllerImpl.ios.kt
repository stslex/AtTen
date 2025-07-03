package com.stslex.atten.core.auth.controller

import androidx.compose.runtime.Composable
import com.stslex.atten.core.auth.callback.GoogleAuthCallback
import com.stslex.atten.core.auth.model.GoogleAuthResult
import com.stslex.atten.core.ui.kit.utils.ActivityHolder

internal actual class GoogleAuthControllerImpl actual constructor(
    callback: GoogleAuthCallback,
    activityHolder: ActivityHolder
) : GoogleAuthController {

    @Composable
    actual override fun RegisterLauncher() {
        // do nothing - need for android
    }

    actual override fun auth(block: (Result<GoogleAuthResult>) -> Unit) {
        TODO("Not yet implemented")
    }
}