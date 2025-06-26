package com.stslex.atten.core.ui.navigation

interface Navigator {

    fun navigateTo(
        screen: Screen,
        options: NavigatorOptions = NavigatorOptions()
    )

    fun popBack()
}
