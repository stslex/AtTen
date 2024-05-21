package com.stslex.atten

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun AppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
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