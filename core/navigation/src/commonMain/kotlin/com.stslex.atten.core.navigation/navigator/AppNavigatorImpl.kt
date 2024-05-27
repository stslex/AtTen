package com.stslex.atten.core.navigation.navigator

import androidx.navigation.NavHostController
import com.stslex.atten.core.Logger
import com.stslex.atten.core.navigation.navigator.AppNavigator.Companion.TAG

class AppNavigatorImpl(
    private val navController: NavHostController
) : AppNavigator {

    override fun navigate(screen: NavigationTarget) {
        when (screen) {
            is NavigationTarget.PopBack -> navController.popBackStack()
            is NavigationTarget.Screen -> navigateScreen(screen)
        }
    }

    private fun navigateScreen(screen: NavigationTarget.Screen) {
        val currentRoute = navController.currentDestination?.route ?: return
        if (currentRoute == screen.screenRoute) return

        try {
            navController.navigate(screen.route) {
                if (screen.isSingleTop.not()) return@navigate

                popUpTo(currentRoute) {
                    inclusive = true
                    saveState = true
                }
                launchSingleTop = true
            }
        } catch (exception: Exception) {
            Logger.exception(exception, TAG, "screen: $screen")
        }
    }
}