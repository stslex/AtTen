package com.stslex.atten.feature.home.navigation

import com.stslex.atten.core.ui.navigation.Component
import com.stslex.atten.core.ui.navigation.Navigator
import com.stslex.atten.feature.home.ui.store.HomeStoreComponent.Navigation

class HomeRouterImpl(
    private val navigator: Navigator
) : HomeRouter {

    override fun invoke(event: Navigation) {
        when (event) {
            is Navigation.NavigateToDetail -> navigator.navigateTo(Component.DetailScreen(event.id))
            is Navigation.Back -> navigator.popBack()
        }
    }
}