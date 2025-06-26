package com.stslex.atten.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute

inline fun <reified S : Screen> NavGraphBuilder.navScreen(
    noinline content: @Composable (S) -> Unit
) {
    composable<S> { backStackEntry ->
        content(backStackEntry.toRoute())
    }
}
