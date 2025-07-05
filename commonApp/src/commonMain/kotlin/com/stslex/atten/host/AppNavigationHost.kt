package com.stslex.atten.host

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.stslex.atten.feature.settings.ui.SettingsScreen
import com.stslex.atten.feature.details.ui.DetailsScreen
import com.stslex.atten.feature.home.ui.HomeScreen

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
            is RootComponent.Child.Details -> DetailsScreen(instance.component)
            is RootComponent.Child.Home -> HomeScreen(instance.component)
            is RootComponent.Child.Settings -> SettingsScreen(instance.component)
        }
    }
}