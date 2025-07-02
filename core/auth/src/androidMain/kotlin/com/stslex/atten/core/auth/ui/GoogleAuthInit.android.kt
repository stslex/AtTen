package com.stslex.atten.core.auth.ui

import android.app.Activity
import android.content.IntentSender.SendIntentException
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.coroutineScope
import com.google.android.gms.auth.api.identity.AuthorizationRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.Scope
import com.stslex.atten.core.auth.model.AuthEvent
import com.stslex.atten.core.auth.model.GoogleAuthResult
import com.stslex.atten.core.auth.state.GoogleAuthState
import com.stslex.atten.core.core.logger.Log
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@Composable
internal actual fun GoogleAuthPlatformInit(state: GoogleAuthState) {
    val activity = requireNotNull(LocalActivity.current)
    val lifecycleOwner = LocalLifecycleOwner.current

    val launcher = rememberLauncher(state::consumeResult)

    DisposableEffect(lifecycleOwner) {
        Log.tag("GOOGLE_AUTH").d("launch_auth")
        val eventJob = state.event
            .onEach { event ->
                Log.tag("GOOGLE_AUTH").d("collect: $event")
                when (event) {
                    AuthEvent.Auth -> processAuth(
                        activity = activity,
                        launcher = launcher,
                        processResult = state::consumeResult
                    )

                    AuthEvent.Cancel -> Unit
                }
            }.launchIn(lifecycleOwner.lifecycle.coroutineScope)

        onDispose {
            Log.tag("GOOGLE_AUTH").d("dispose_auth")
            eventJob.cancel()
        }
    }
}

@Composable
private fun rememberLauncher(
    processResult: (Result<GoogleAuthResult>) -> Unit
): ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult> =
    checkNotNull(LocalActivity.current).let { activity ->
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            Log.tag("GOOGLE_AUTH").d("activity_result: $result, ${result.data}")
            result.data?.extras?.keySet()?.forEach { key ->
                Log.tag("GOOGLE_AUTH").d("[$key] = ${result.data?.extras?.get(key)}")
            }
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
            processResult(result)
        }
    }


// todo replace with handler in di (not ui object)
private fun processAuth(
    activity: Activity,
    launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>,
    processResult: (Result<GoogleAuthResult>) -> Unit
) {
    val authRequest = AuthorizationRequest.Builder()
        .setRequestedScopes(listOf(Scope(Scopes.EMAIL)))
        .build()
    Identity.getAuthorizationClient(activity)
        .authorize(authRequest)
        .addOnCanceledListener {
            Log.tag("GOOGLE_AUTH").d("addOnCanceledListener")
        }
        .addOnFailureListener { e ->
            Log.tag("GOOGLE_AUTH").e(e, "addOnFailureListener")
            processResult(Result.failure(e))
        }
        .addOnSuccessListener { result ->
            Log.tag("GOOGLE_AUTH").d("on success: $result")
            if (result.hasResolution()) {
                try {
                    val pendingIntent = checkNotNull(result.pendingIntent)
                    val intentSenderRequest = IntentSenderRequest.Builder(pendingIntent).build()
                    launcher.launch(intentSenderRequest)
                } catch (e: SendIntentException) {
                    Log.e(
                        e,
                        "Couldn't start Authorization UI: " + e.localizedMessage
                    )
                }
            } else {
                val uiResult = GoogleAuthResult(
                    accessToken = result.accessToken,
                    serverAuthCode = result.serverAuthCode
                )
                processResult(Result.success(uiResult))
            }
        }
}
