package com.stslex.atten.feature.details.di

import androidx.compose.runtime.Composable
import com.stslex.atten.core.ui.mvi.Feature
import com.stslex.atten.core.ui.mvi.processor.StoreProcessor
import com.stslex.atten.core.ui.mvi.processor.rememberStoreProcessor
import com.stslex.atten.feature.details.ui.mvi.DetailsComponent
import com.stslex.atten.feature.details.ui.mvi.DetailsStore.Action
import com.stslex.atten.feature.details.ui.mvi.DetailsStore.Event
import com.stslex.atten.feature.details.ui.mvi.DetailsStore.State
import org.koin.core.module.Module
import org.koin.ksp.generated.module

internal typealias DetailsStoreProcessor = StoreProcessor<State, Action, Event>

/**
 * DetailsFeature is a Koin feature module that provides the DetailsStore processor.
 * It is responsible for managing the state and actions related to the profile feature.
 *
 * @see [com.stslex.atten.feature.details.ui.mvi.DetailsStore]
 * */
internal object DetailsFeature : Feature<DetailsStoreProcessor, DetailsComponent>(
    scopeClass = DetailsScope::class
) {

    override val module: Module by lazy { ModuleFeatureDetails().module }

    @Composable
    override fun processor(
        component: DetailsComponent
    ): DetailsStoreProcessor = rememberStoreProcessor(component)
}
