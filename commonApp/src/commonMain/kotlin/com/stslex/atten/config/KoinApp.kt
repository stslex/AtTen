package com.stslex.atten.config

import androidx.compose.runtime.Composable
import com.stslex.atten.di.appModules
import org.koin.compose.KoinApplication
import org.koin.core.KoinApplication

@Composable
fun KoinApp(
    additionalSetup: KoinApplication.() -> Unit = {},
    content: @Composable () -> Unit
) {
    KoinApplication(
        application = {
            modules(appModules)
            additionalSetup()
        },
        content = content
    )
}
