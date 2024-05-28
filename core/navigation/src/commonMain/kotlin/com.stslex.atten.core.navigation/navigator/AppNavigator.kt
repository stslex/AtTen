package com.stslex.atten.core.navigation.navigator

interface AppNavigator {

    fun navigate(screen: NavigationTarget)

    companion object {

        const val TAG = "AppNavigator"
    }
}

