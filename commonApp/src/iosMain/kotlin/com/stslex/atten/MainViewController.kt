package com.stslex.atten

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.ApplicationLifecycle
import com.stslex.atten.host.DefaultRootComponent

fun MainViewController() = ComposeUIViewController {
    val root = remember {
        DefaultRootComponent(DefaultComponentContext(ApplicationLifecycle()))
    }
    App(root)
}
