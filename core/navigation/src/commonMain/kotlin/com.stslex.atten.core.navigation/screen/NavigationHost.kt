package com.stslex.atten.core.navigation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.stslex.atten.core.navigation.decompose.RootComponent

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun NavigationHost(
    root: RootComponent,
    screen: @Composable (Configuration) -> Unit
) {
    root.bindNavigator()
    val childStack by root.childStack.subscribeAsState()
    Children(
        stack = childStack,
        animation = predictiveBackAnimation(
            backHandler = root.backHandler,
            fallbackAnimation = stackAnimation(scale()),
            onBack = root::onBack,
        )
    ) { child ->
        screen(child.configuration)
    }
}