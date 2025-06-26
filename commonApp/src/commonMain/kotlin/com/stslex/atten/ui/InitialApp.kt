package com.stslex.atten.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.stslex.atten.core.ui.navigation.Component
import com.stslex.atten.feature.details.navigation.detailsGraph
import com.stslex.atten.feature.home.navigation.homeGraph

@Composable
internal fun InitialApp(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        NavHost(
            navController = navHostController,
            startDestination = Component.Home
        ) {
            homeGraph()
            detailsGraph()
        }
    }
}