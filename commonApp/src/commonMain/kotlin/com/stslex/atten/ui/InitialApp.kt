package com.stslex.atten.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.stslex.atten.core.navigation.decompose.RootComponent
import com.stslex.atten.core.navigation.screen.Configuration
import com.stslex.atten.core.navigation.screen.NavigationHost
import com.stslex.atten.feature.details.navigation.DetailsInitScreen
import com.stslex.atten.feature.home.navigation.HomeInitScreen

@Composable
internal fun InitialApp(
    root: RootComponent,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        NavigationHost(root) { screen ->
            when (screen) {
                is Configuration.HomeScreen -> HomeInitScreen()
                is Configuration.DetailScreen -> DetailsInitScreen(
                    id = screen.id
                )
            }
        }
    }
}