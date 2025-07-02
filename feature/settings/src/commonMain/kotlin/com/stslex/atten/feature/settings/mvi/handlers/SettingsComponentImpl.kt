package com.stslex.atten.feature.settings.mvi.handlers

import com.arkivanov.decompose.ComponentContext
import com.stslex.atten.feature.settings.mvi.SettingsComponent
import com.stslex.atten.feature.settings.mvi.SettingsHandlerStore
import com.stslex.atten.feature.settings.mvi.SettingsStore.Action.Navigation
import com.stslex.atten.core.ui.navigation.Router

class SettingsComponentImpl(
    private val router: Router
) : SettingsComponent, ComponentContext by router {

    override fun SettingsHandlerStore.invoke(action: Navigation) {
        when (action) {
            Navigation.NavBack -> router.popBack()
        }
    }
}