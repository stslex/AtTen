package com.stslex.atten.feature.details.ui.mvi

import com.arkivanov.decompose.ComponentContext
import com.stslex.atten.core.ui.navigation.Component
import com.stslex.atten.feature.details.ui.mvi.DetailsStore.Action
import com.stslex.atten.feature.details.ui.mvi.handlers.DetailsComponentImpl
import com.stslex.atten.core.ui.mvi.handler.Handler

interface DetailsComponent : Component, Handler<Action.Navigation, DetailsHandlerStore> {

    val uuid: String

    companion object {

        fun create(
            popBack: () -> Unit,
            uuid: String,
            componentContext: ComponentContext
        ): DetailsComponent = DetailsComponentImpl(
            componentContext = componentContext,
            popBack = popBack,
            uuid = uuid
        )
    }
}