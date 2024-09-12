package com.stslex.atten.core.navigation.navigator

import com.stslex.atten.core.navigation.NavigatorOptions
import com.stslex.atten.core.navigation.Screen

interface Navigator {

    fun navigateTo(
        screen: Screen,
        options: NavigatorOptions = NavigatorOptions()
    )

    fun popBack()
}
