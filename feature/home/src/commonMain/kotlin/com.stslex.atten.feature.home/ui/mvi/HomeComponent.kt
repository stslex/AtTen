package com.stslex.atten.feature.home.ui.mvi

import com.arkivanov.decompose.ComponentContext
import com.stslex.atten.core.ui.navigation.Component
import com.stslex.atten.core.ui.navigation.Config
import com.stslex.atten.feature.home.ui.mvi.HomeStore.Action
import com.stslex.atten.feature.home.ui.mvi.handlers.HomeComponentImpl
import com.stslex.atten.core.ui.mvi.handler.Handler

interface HomeComponent : Component, Handler<Action.Navigation, HomeHandlerStore> {

    companion object {

        fun create(
            popBack: () -> Unit,
            navTo: (config: Config) -> Unit,
            componentContext: ComponentContext
        ): HomeComponent = HomeComponentImpl(
            popBack = popBack,
            navTo = navTo,
            componentContext = componentContext
        )
    }
}