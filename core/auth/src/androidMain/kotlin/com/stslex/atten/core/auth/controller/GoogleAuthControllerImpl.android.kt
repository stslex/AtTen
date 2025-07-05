package com.stslex.atten.core.auth.controller

import android.app.Activity
import android.content.IntentSender.SendIntentException
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import com.google.android.gms.auth.api.identity.AuthorizationRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.Scope
import com.stslex.atten.core.auth.callback.GoogleAuthCallback
import com.stslex.atten.core.auth.model.GoogleAuthResult
import com.stslex.atten.core.core.logger.Log
import com.stslex.atten.core.ui.kit.utils.ActivityHolder

internal actual class GoogleAuthControllerImpl actual constructor(
    private val callback: GoogleAuthCallback,
    private val activityHolder: ActivityHolder
) : GoogleAuthController {

    private lateinit var launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>
    private val activity: ComponentActivity
        get() = requireNotNull(activityHolder.activity as? ComponentActivity)

    @Composable
    actual override fun RegisterLauncher() {
        launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            logger.d("activity_result: $result, ${result.data}")
            val activity = checkNotNull(activityHolder.activity as? ComponentActivity)
            val result = if (result.resultCode == Activity.RESULT_OK) {
                val authorizationResult = Identity
                    .getAuthorizationClient(activity)
                    .getAuthorizationResultFromIntent(result.data)
                val uiResult = GoogleAuthResult(
                    accessToken = authorizationResult.accessToken,
                    serverAuthCode = authorizationResult.serverAuthCode
                )
                Result.success(uiResult)
            } else {
                val msg = "auth fail with ${result.resultCode} code"
                Result.failure(IllegalStateException(msg))
            }
            callback.process(result)
        }
    }

    actual override fun auth(
        block: (Result<GoogleAuthResult>) -> Unit
    ) {
        callback { block(it) }

        val authRequest = AuthorizationRequest.Builder()
            .setRequestedScopes(listOf(Scope(Scopes.EMAIL)))
            .build()

        Identity.getAuthorizationClient(activity)
            .authorize(authRequest)
            .addOnSuccessListener { result ->
                logger.d("on success: $result")
                if (result.hasResolution()) {
                    try {
                        val pendingIntent = checkNotNull(result.pendingIntent)
                        val intentSenderRequest = IntentSenderRequest.Builder(pendingIntent).build()
                        launcher.launch(intentSenderRequest)
                    } catch (e: SendIntentException) {
                        logger.e(e, "Couldn't start Authorization UI: " + e.localizedMessage)
                    }
                } else {
                    val uiResult = GoogleAuthResult(
                        accessToken = result.accessToken,
                        serverAuthCode = result.serverAuthCode
                    )
                    callback.process(Result.success(uiResult))
                }
            }
            .addOnFailureListener {
                logger.e(it)
                callback.process(Result.failure(it))
            }
    }

    companion object {

        private val logger = Log.tag(TAG)
        private const val TAG = "GOOGLE_AUTH_CONTROLLER"
    }
}