package com.stslex.atten.feature.home.ui.mvi

import com.stslex.atten.core.ui.mvi.handler.Handler
import com.stslex.atten.core.ui.navigation.Component
import com.stslex.atten.core.ui.navigation.Router
import com.stslex.atten.feature.home.ui.mvi.HomeStore.Action
import com.stslex.atten.feature.home.ui.mvi.handlers.HomeComponentImpl

interface HomeComponent : Component, Handler<Action.Navigation, HomeHandlerStore> {

    companion object {

        fun create(router: Router): HomeComponent = HomeComponentImpl(router)
    }
}