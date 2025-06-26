package com.stslex.atten

import androidx.compose.runtime.Composable
import com.stslex.atten.config.KoinApp
import com.stslex.atten.core.ui.kit.theme.AppTheme
import com.stslex.atten.ui.InitialApp
import org.koin.core.KoinApplication

@Composable
fun App(
    onThemeChange: (isDark: Boolean) -> Unit = {},
    additionalSetup: KoinApplication.() -> Unit = {},
) {
    KoinApp(additionalSetup) { controller ->
        AppTheme(
            onThemeChange = onThemeChange
        ) {
            InitialApp(
                navHostController = controller
            )
        }
    }
}
