package com.stslex.atten.feature.home.navigation

import com.stslex.atten.core.navigation.navigator.AppNavigator
import com.stslex.atten.core.navigation.screen.AppScreen
import com.stslex.atten.feature.home.ui.store.HomeStoreComponent.Navigation

class HomeRouterImpl(
    private val navigator: AppNavigator
) : HomeRouter {

    override fun invoke(event: Navigation) {
        when (event) {
            is Navigation.NavigateToDetail -> navigator.navigate(AppScreen.Details(event.id))
        }
    }
}