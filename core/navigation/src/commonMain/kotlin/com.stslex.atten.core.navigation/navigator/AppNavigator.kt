package com.stslex.atten.core.navigation.navigator

import com.stslex.atten.core.navigation.screen.Configuration

interface AppNavigator {

    fun navigateTo(config: Configuration)

    fun popBack()
}
