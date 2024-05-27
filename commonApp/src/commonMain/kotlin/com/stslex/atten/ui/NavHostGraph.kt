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
import org.koin.compose.getKoin

private var localId = 0

@Composable
fun NavHostGraph() {
    AppHostGraph { screen ->
        val navigator = getKoin().get<AppNavigator>()
        when (screen) {
            AppScreen.Home -> {
                Column {
                    Text("Home")
                    Button(
                        onClick = {
                            navigator.navigate(
                                AppScreen.Details(id = localId++)
                            )
                        }
                    ) {
                        Text("Go to Details")
                    }
                }
            }

            is AppScreen.Details -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                ) {
                    Text("Details :${screen.id}")
                    Button(
                        onClick = {
                            navigator.navigate(
                                AppScreen.Home
                            )
                        }
                    ) {
                        Text("Go to Home")
                    }
                }
            }
        }
    }
}