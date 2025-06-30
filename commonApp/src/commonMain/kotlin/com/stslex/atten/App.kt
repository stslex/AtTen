package com.stslex.atten

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.stslex.atten.core.ui.kit.theme.AppTheme
import com.stslex.atten.host.AppNavigationHost
import com.stslex.atten.host.RootComponent

@Composable
fun App(
    rootComponent: RootComponent,
    onThemeChange: (isDark: Boolean) -> Unit = {},
) {
    AppTheme(
        onThemeChange = onThemeChange
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        ) { paddingValues ->
            AppNavigationHost(
                modifier = Modifier.padding(
                    bottom = paddingValues.calculateBottomPadding()
                ),
                rootComponent = rootComponent
            )
        }
    }
}
