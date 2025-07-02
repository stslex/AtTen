package com.stslex.atten.feature.home.ui.mvi.handlers

import com.arkivanov.decompose.ComponentContext
import com.stslex.atten.core.ui.navigation.Config
import com.stslex.atten.core.ui.navigation.Router
import com.stslex.atten.feature.home.ui.mvi.HomeComponent
import com.stslex.atten.feature.home.ui.mvi.HomeHandlerStore
import com.stslex.atten.feature.home.ui.mvi.HomeStore.Action

internal class HomeComponentImpl(
    private val router: Router,
) : HomeComponent, ComponentContext by router {

    override fun HomeHandlerStore.invoke(action: Action.Navigation) {
        when (action) {
            is Action.Navigation.Back -> router.popBack()
            is Action.Navigation.NavigateToDetail -> router.navTo(Config.Detail(action.id))
        }
    }
}