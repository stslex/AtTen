package com.stslex.atten

import androidx.compose.runtime.Composable
import com.stslex.atten.config.KoinApp
import com.stslex.atten.core.ui.theme.AppTheme
import com.stslex.atten.ui.InitialApp
import org.koin.core.KoinApplication

@Composable
fun App(
    additionalSetup: KoinApplication.() -> Unit = {},
) {
    KoinApp(additionalSetup) {
        AppTheme {
            InitialApp()
        }
    }
}
