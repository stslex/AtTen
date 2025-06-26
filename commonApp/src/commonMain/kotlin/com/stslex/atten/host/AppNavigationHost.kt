package com.stslex.atten.host

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation

@Composable
internal fun AppNavigationHost(
    rootComponent: RootComponent,
    modifier: Modifier = Modifier,
) {
    Children(
        stack = rootComponent.stack,
        modifier = modifier.fillMaxSize(),
        animation = stackAnimation(),
    ) { created ->
        when (val instance = created.instance) {
            is RootComponent.Child.Details -> TODO()
            is RootComponent.Child.Home -> TODO()
        }
    }
}