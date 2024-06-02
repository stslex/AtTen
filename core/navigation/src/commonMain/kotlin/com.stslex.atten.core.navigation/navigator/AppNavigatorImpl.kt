package com.stslex.atten.core.navigation.navigator

import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.stslex.atten.core.Logger
import com.stslex.atten.core.navigation.screen.Configuration

class AppNavigatorImpl(
    private val navigation: StackNavigation<Configuration>
) : AppNavigator {

    override fun navigateTo(config: Configuration) {
        navigation.push(config) {
            Logger.d("navigateTo config: $config")
        }
    }

    override fun popBack() {
        navigation.pop { isSuccess ->
            Logger.d("popBack isSuccess: $isSuccess")
        }
    }
}