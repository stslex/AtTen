package com.stslex.atten.feature.settings.ui

import androidx.compose.runtime.Composable
import com.stslex.atten.feature.settings.di.SettingsFeature
import com.stslex.atten.feature.settings.mvi.SettingsComponent
import com.stslex.atten.core.ui.mvi.NavComponentScreen

@Composable
fun SettingsScreen(
    component: SettingsComponent
) {
    NavComponentScreen(SettingsFeature, component) { processor ->
        SettingsWidget(
            state = processor.state.value,
            consume = processor::consume,
        )
    }
}