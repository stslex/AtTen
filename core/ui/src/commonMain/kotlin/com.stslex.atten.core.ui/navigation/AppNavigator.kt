package com.stslex.atten.core.ui.navigation

interface AppNavigator {

    val currentScreen: AppScreen?

    fun navigate(screen: AppScreen)
}