package com.stslex.atten.core.ui.mvi

import androidx.compose.runtime.Composable
import com.stslex.atten.core.ui.mvi.processor.StoreProcessor
import com.stslex.atten.core.ui.navigation.Component
import org.koin.compose.module.rememberKoinModules
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Suppress("UNCHECKED_CAST")
@Composable
fun <TProcessor : StoreProcessor<*, *, *>, TComponent : Component> NavComponentScreen(
    feature: Feature<TProcessor, TComponent>,
    component: TComponent,
    content: @Composable (TProcessor) -> Unit
) {
    if (feature.loadModule) {
        rememberKoinModules(unloadModules = true) {
            listOf(feature.module)
        }
    }

    content(feature.processor(component))
}
