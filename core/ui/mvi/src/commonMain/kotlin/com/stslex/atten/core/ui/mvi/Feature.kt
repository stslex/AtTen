package com.stslex.atten.core.ui.mvi

import androidx.compose.runtime.Composable
import com.stslex.atten.core.ui.mvi.processor.StoreProcessor
import com.stslex.atten.core.ui.navigation.Component
import org.koin.core.component.KoinScopeComponent
import org.koin.core.module.Module

/**
 * Feature is a Koin feature module that provides a StoreProcessor.
 * It is responsible for managing the state and actions related to the feature.
 *
 * @see [StoreProcessor]
 * */
interface Feature<TProcessor : StoreProcessor<*, *, *>, TComponent : Component> :
    KoinScopeComponent {

    val loadModule: Boolean
        get() = false

    val bindWithLifecycle: Boolean
        get() = true

    val module: Module

    @Composable
    fun processor(component: TComponent): TProcessor
}