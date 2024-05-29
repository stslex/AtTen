package com.stslex.atten

import androidx.compose.runtime.Composable
import com.stslex.atten.config.KoinApp
import com.stslex.atten.core.navigation.decompose.RootComponent
import com.stslex.atten.core.ui.theme.AppTheme
import com.stslex.atten.ui.InitialApp
import org.koin.core.KoinApplication

@Composable
fun App(
    root: RootComponent,
    onThemeChange: (isDark: Boolean) -> Unit = {},
    additionalSetup: KoinApplication.() -> Unit = {},
) {
    KoinApp(additionalSetup) {
        AppTheme(
            onThemeChange = onThemeChange
        ) {
            InitialApp(root = root)
        }
    }
}
