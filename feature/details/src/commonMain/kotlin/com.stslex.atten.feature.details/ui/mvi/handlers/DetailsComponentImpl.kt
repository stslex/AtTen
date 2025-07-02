package com.stslex.atten.feature.details.ui.mvi.handlers

import com.arkivanov.decompose.ComponentContext
import com.stslex.atten.core.ui.navigation.Router
import com.stslex.atten.feature.details.ui.mvi.DetailsComponent
import com.stslex.atten.feature.details.ui.mvi.DetailsHandlerStore
import com.stslex.atten.feature.details.ui.mvi.DetailsStore.Action

internal class DetailsComponentImpl(
    private val router: Router,
    override val uuid: String
) : DetailsComponent, ComponentContext by router {

    override fun DetailsHandlerStore.invoke(action: Action.Navigation) {
        when (action) {
            Action.Navigation.Back -> router.popBack()
        }
    }
}