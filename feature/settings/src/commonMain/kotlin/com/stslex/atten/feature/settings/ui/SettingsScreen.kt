package com.stslex.atten.feature.settings.ui

import androidx.compose.runtime.Composable
import com.stslex.atten.core.auth.controller.GoogleAuthController
import com.stslex.atten.core.ui.mvi.NavComponentScreen
import com.stslex.atten.feature.settings.di.SettingsFeature
import com.stslex.atten.feature.settings.mvi.SettingsComponent
import org.koin.compose.getKoin

@Composable
fun SettingsScreen(
    component: SettingsComponent
) {
    NavComponentScreen(SettingsFeature, component) { processor ->
        getKoin().get<GoogleAuthController>().RegisterLauncher()
        SettingsWidget(
            state = processor.state.value,
            consume = processor::consume,
        )
    }
}