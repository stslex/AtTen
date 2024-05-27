package com.stslex.atten.core.navigation

import androidx.navigation.NavBackStackEntry
import com.stslex.atten.core.navigation.screen.AppScreen
import com.stslex.atten.core.navigation.screen.AppScreenHost

internal fun NavBackStackEntry.getDestination(
    appScreenHost: AppScreenHost
): AppScreen = when (appScreenHost) {
    AppScreenHost.HOME -> AppScreen.Home
    AppScreenHost.ToDoDetail -> AppScreen.Details(id = getInt("id"))
}

internal fun NavBackStackEntry.getInt(key: String): Int =
    requireNotNull(arguments?.getString(key)?.toIntOrNull()) {
        "$key is required"
    }

internal fun NavBackStackEntry.getString(key: String): String =
    requireNotNull(arguments?.getString(key)) {
        "$key is required"
    }