package com.stslex.atten.feature.details.ui.mvi

import com.stslex.atten.core.ui.mvi.handler.Handler
import com.stslex.atten.core.ui.navigation.Component
import com.stslex.atten.core.ui.navigation.Router
import com.stslex.atten.feature.details.ui.mvi.DetailsStore.Action
import com.stslex.atten.feature.details.ui.mvi.handlers.DetailsComponentImpl

interface DetailsComponent : Component, Handler<Action.Navigation, DetailsHandlerStore> {

    val uuid: String

    companion object {

        fun create(
            router: Router,
            uuid: String,
        ): DetailsComponent = DetailsComponentImpl(router, uuid)
    }
}