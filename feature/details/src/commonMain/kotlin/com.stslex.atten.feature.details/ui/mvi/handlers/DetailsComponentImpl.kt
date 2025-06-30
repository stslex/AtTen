package com.stslex.atten.feature.details.ui.mvi.handlers

import com.arkivanov.decompose.ComponentContext
import com.stslex.atten.feature.details.ui.mvi.DetailsComponent
import com.stslex.atten.feature.details.ui.mvi.DetailsHandlerStore
import com.stslex.atten.feature.details.ui.mvi.DetailsStore

internal class DetailsComponentImpl(
    componentContext: ComponentContext,
    private val popBack: () -> Unit,
    override val uuid: String
) : DetailsComponent, ComponentContext by componentContext {

    override fun DetailsHandlerStore.invoke(action: DetailsStore.Action.Navigation) {
        when (action) {
            DetailsStore.Action.Navigation.Back -> popBack()
        }
    }
}