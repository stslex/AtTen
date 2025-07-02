package com.stslex.atten.feature.settings.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.stslex.atten.core.auth.model.AuthEvent
import com.stslex.atten.core.auth.state.GoogleAuthStateImpl.Companion.rememberGoogleAuthState
import com.stslex.atten.core.auth.ui.GoogleAuthInit
import com.stslex.atten.core.core.logger.Log
import com.stslex.atten.core.ui.mvi.NavComponentScreen
import com.stslex.atten.feature.settings.di.SettingsFeature
import com.stslex.atten.feature.settings.mvi.SettingsComponent
import com.stslex.atten.feature.settings.mvi.SettingsStore

@Composable
fun SettingsScreen(
    component: SettingsComponent
) {
    NavComponentScreen(SettingsFeature, component) { processor ->
        val googleAuthState = rememberGoogleAuthState()

        processor.handle { event ->
            when (event) {
                SettingsStore.Event.GoogleAuth -> googleAuthState.consume(AuthEvent.Auth)
            }
        }

        LaunchedEffect(Unit) {
            googleAuthState.state.collect {
                Log.tag("GOOGLE_AUTH_SETTINGS").d("collected: $it")
            }
        }

        GoogleAuthInit(googleAuthState)

        SettingsWidget(
            state = processor.state.value,
            consume = processor::consume,
        )
    }
}