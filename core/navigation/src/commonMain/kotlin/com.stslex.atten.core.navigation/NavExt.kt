package com.stslex.atten.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.arkivanov.essenty.backhandler.BackCallback
import com.arkivanov.essenty.backhandler.BackHandler
import com.stslex.atten.core.navigation.decompose.AppComponents

@Composable
fun AppComponents.AppBackHandler(
    enabled: Boolean,
    onBack: () -> Unit
) {
    val callback = remember(this) {
        object : BackCallback() {
            override fun onBack() {
                onBack()
            }
        }
    }

    LaunchedEffect(enabled, callback) {
        if (enabled) {
            backHandler.registerSafe(callback)
        } else {
            backHandler.unregisterSafe(callback)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            backHandler.unregisterSafe(callback)
        }
    }
}

private fun BackHandler.registerSafe(callback: BackCallback) {
    if (isRegistered(callback).not()) {
        register(callback)
    }
}

private fun BackHandler.unregisterSafe(callback: BackCallback) {
    if (isRegistered(callback)) {
        unregister(callback)
    }
}