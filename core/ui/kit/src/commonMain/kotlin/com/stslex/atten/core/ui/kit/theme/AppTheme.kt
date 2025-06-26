package com.stslex.atten.core.ui.kit.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun AppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    onThemeChange: (isDark: Boolean) -> Unit = {},
    content: @Composable () -> Unit,
) {
    LaunchedEffect(isDarkTheme) {
        onThemeChange(isDarkTheme)
    }
    MaterialTheme(
        colorScheme = appColorScheme(isDarkTheme),
        content = content
    )
}

@Composable
internal expect fun appColorScheme(isDarkTheme: Boolean): ColorScheme
