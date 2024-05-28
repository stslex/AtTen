package com.stslex.atten.core.navigation.screen

import com.stslex.atten.core.navigation.navigator.NavigationTarget

sealed class AppScreen(
    private val screen: AppScreenHost,
    private vararg val args: String,
    override val isSingleTop: Boolean = false,
) : NavigationTarget.Screen {

    data object Home : AppScreen(
        screen = AppScreenHost.HOME,
        isSingleTop = true
    )

    data class Details(
        val id: Long
    ) : AppScreen(
        screen = AppScreenHost.ToDoDetail,
        id.toString()
    )

    private val argumentsForRoute: String
        get() = when (args.isEmpty()) {
            true -> String()
            false -> args.joinToString(separator = "/", prefix = "/")
        }

    override val route: String
        get() = "${screen.destinationName}$argumentsForRoute"

    override val screenRoute: String
        get() = screen.route
}
