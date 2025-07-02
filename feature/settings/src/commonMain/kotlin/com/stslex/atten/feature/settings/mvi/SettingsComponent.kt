package com.stslex.atten.feature.settings.mvi

import com.stslex.atten.feature.settings.mvi.SettingsStore.Action
import com.stslex.atten.feature.settings.mvi.handlers.SettingsComponentImpl
import com.stslex.atten.core.ui.mvi.handler.Handler
import com.stslex.atten.core.ui.navigation.Component
import com.stslex.atten.core.ui.navigation.Router

interface SettingsComponent : Component, Handler<Action.Navigation, SettingsHandlerStore> {

    companion object {

        fun create(router: Router): SettingsComponent = SettingsComponentImpl(router)
    }
}