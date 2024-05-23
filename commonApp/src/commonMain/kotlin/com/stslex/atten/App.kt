package com.stslex.atten

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun App() {
    // todo replace to :core:ui:theme:AppTheme
    AppTheme {
        InitialApp()
    }
}

@Composable
fun InitialApp(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Text(
            text = "Hello, World!",
            style = MaterialTheme.typography.headlineLarge
        )
    }
}