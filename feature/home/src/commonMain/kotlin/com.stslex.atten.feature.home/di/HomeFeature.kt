package com.stslex.atten.feature.home.di

import androidx.compose.runtime.Composable
import com.stslex.atten.core.ui.mvi.Feature
import com.stslex.atten.core.ui.mvi.processor.StoreProcessor
import com.stslex.atten.core.ui.mvi.processor.rememberStoreProcessor
import com.stslex.atten.feature.home.ui.mvi.HomeComponent
import com.stslex.atten.feature.home.ui.mvi.HomeStore.Action
import com.stslex.atten.feature.home.ui.mvi.HomeStore.Event
import com.stslex.atten.feature.home.ui.mvi.HomeStore.State
import org.koin.core.module.Module
import org.koin.ksp.generated.module

internal typealias HomeStoreProcessor = StoreProcessor<State, Action, Event>

/**
 * HomeFeature is a Koin feature module that provides the HomeStore processor.
 * It is responsible for managing the state and actions related to the profile feature.
 *
 * @see [com.stslex.atten.feature.home.ui.mvi.HomeStore]
 * */
internal object HomeFeature : Feature<HomeStoreProcessor, HomeComponent>(
    scopeClass = HomeScope::class
) {

    override val module: Module by lazy { ModuleFeatureHome().module }

    @Composable
    override fun processor(
        component: HomeComponent
    ): HomeStoreProcessor = rememberStoreProcessor(component)
}
