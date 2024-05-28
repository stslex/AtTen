package com.stslex.atten.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.stslex.atten.core.Logger
import com.stslex.atten.core.navigation.di.ModuleCoreNavigation
import com.stslex.atten.core.navigation.navigator.AppNavigator
import com.stslex.atten.core.navigation.screen.AppScreen
import com.stslex.atten.core.navigation.screen.AppScreenHost
import org.koin.compose.getKoin

@Composable
fun AppHostGraph(
    screen: @Composable (AppScreen) -> Unit
) {
    val navHostController = rememberNavController()
    getKoin().loadModules(listOf(ModuleCoreNavigation().create(navHostController)))
    NavHost(
        navController = navHostController,
        startDestination = AppScreenHost.HOME.route
    ) {
        AppScreenHost.entries.forEach { screenHost ->
            composable(
                route = screenHost.route,
                arguments = screenHost.composableArguments
            ) { navBackStackEntry ->
                val appScreenDestination = try {
                    navBackStackEntry.getDestination(screenHost)
                } catch (e: IllegalArgumentException) {
                    Logger.exception(
                        throwable = e,
                        tag = AppNavigator.TAG,
                        message = "screenHost: $screenHost"
                    )
                    AppScreen.Home
                }
                screen(appScreenDestination)
            }
        }
    }
}