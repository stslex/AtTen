package com.stslex.atten.config

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.stslex.atten.di.appModules
import org.koin.compose.KoinApplication
import org.koin.core.KoinApplication

@Composable
fun KoinApp(
    additionalSetup: KoinApplication.() -> Unit = {},
    content: @Composable (navHostController: NavHostController) -> Unit
) {
    val navHostController = rememberNavController()
    KoinApplication(
        application = {
            modules(appModules(navHostController))
            additionalSetup()
        },
        content = {
            content(navHostController)
        }
    )
}
