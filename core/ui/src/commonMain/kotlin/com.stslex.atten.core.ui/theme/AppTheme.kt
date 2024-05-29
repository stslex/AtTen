package com.stslex.atten.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
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
    val colors = if (isDarkTheme) {
        darkColorScheme()
    } else {
        lightColorScheme()
    }
    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}