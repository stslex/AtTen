package com.stslex.atten.core.navigation.screen

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

enum class AppScreenHost(
    vararg argsNames: String
) {
    HOME,
    ToDoDetail("id");

    val destinationName: String = "${name}_route"
    val route: String = "$destinationName${argsNames.argumentsRoute}"
    val composableArguments: List<NamedNavArgument> = argsNames.map { name ->
        navArgument(name) { NavType.StringType }
    }

    private val Array<out String>.argumentsRoute: String
        get() = if (isEmpty()) "" else joinToString(
            separator = "}/{",
            prefix = "/{",
            postfix = "}"
        )

    companion object {

        fun findByRoute(
            route: String?
        ): AppScreenHost = if (route.isNullOrBlank()) {
            throw IllegalArgumentException("Route is null or blank")
        } else {
            AppScreenHost.entries.firstOrNull { it.route == route }
        } ?: throw IllegalArgumentException("Route not found")
    }
}