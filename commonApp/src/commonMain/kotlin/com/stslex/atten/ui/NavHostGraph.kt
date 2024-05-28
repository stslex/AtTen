package com.stslex.atten.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.stslex.atten.core.navigation.AppHostGraph
import com.stslex.atten.core.navigation.navigator.AppNavigator
import com.stslex.atten.core.navigation.screen.AppScreen
import com.stslex.atten.feature.home.navigation.HomeInitScreen
import org.koin.compose.getKoin

@Composable
fun NavHostGraph() {
    AppHostGraph { screen ->
        val navigator = getKoin().get<AppNavigator>()
        when (screen) {
            AppScreen.Home -> HomeInitScreen()
            is AppScreen.Details -> DetailsInitScreen(
                id = screen.id,
                navHome = { navigator.navigate(AppScreen.Home) }
            )
        }
    }
}

@Composable
fun DetailsInitScreen(
    id: Long,
    navHome: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
    ) {
        Text("Details :$id")
        Button(
            onClick = navHome
        ) {
            Text("Go to Home")
        }
    }
}