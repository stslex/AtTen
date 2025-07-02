package com.stslex.atten.feature.settings.di

import androidx.compose.runtime.Composable
import com.stslex.atten.feature.settings.mvi.SettingsComponent
import com.stslex.atten.feature.settings.mvi.SettingsStore.Action
import com.stslex.atten.feature.settings.mvi.SettingsStore.Event
import com.stslex.atten.feature.settings.mvi.SettingsStore.State
import com.stslex.atten.core.ui.mvi.Feature
import com.stslex.atten.core.ui.mvi.processor.StoreProcessor
import com.stslex.atten.core.ui.mvi.processor.rememberStoreProcessor
import org.koin.core.module.Module
import org.koin.ksp.generated.module

internal typealias SettingsStoreProcessor = StoreProcessor<State, Action, Event>

/**
 * SettingsFeature is a Koin feature module that provides the SettingsStore processor.
 * It is responsible for managing the state and actions related to the profile feature.
 *
 * @see [com.stslex.atten.feature.settings.mvi.SettingsStore]
 * */
internal object SettingsFeature : Feature<SettingsStoreProcessor, SettingsComponent>(
    scopeClass = SettingsScope::class
) {

    override val module: Module by lazy { ModuleFeatureSettings().module }

    @Composable
    override fun processor(
        component: SettingsComponent
    ): SettingsStoreProcessor = rememberStoreProcessor(component)
}
