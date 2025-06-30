package com.stslex.atten.feature.home.ui.mvi.handlers

import com.arkivanov.decompose.ComponentContext
import com.stslex.atten.core.ui.navigation.Config
import com.stslex.atten.feature.home.ui.mvi.HomeComponent
import com.stslex.atten.feature.home.ui.mvi.HomeHandlerStore
import com.stslex.atten.feature.home.ui.mvi.HomeStore.Action

internal class HomeComponentImpl(
    private val popBack: () -> Unit,
    private val navTo: (config: Config) -> Unit,
    componentContext: ComponentContext
) : HomeComponent, ComponentContext by componentContext {

    override fun HomeHandlerStore.invoke(action: Action.Navigation) {
        when (action) {
            is Action.Navigation.Back -> popBack()
            is Action.Navigation.NavigateToDetail -> navTo(Config.Detail(action.id))
        }
    }
}