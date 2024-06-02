package com.stslex.atten.core.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
internal actual fun appColorScheme(
    isDarkTheme: Boolean
): ColorScheme = if (isDarkTheme) {
    darkColorScheme()
} else {
    lightColorScheme()
}