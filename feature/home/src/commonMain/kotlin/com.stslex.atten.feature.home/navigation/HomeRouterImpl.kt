package com.stslex.atten.feature.home.navigation

import com.stslex.atten.core.navigation.navigator.AppNavigator
import com.stslex.atten.core.navigation.screen.Configuration
import com.stslex.atten.feature.home.ui.store.HomeStoreComponent.Navigation

class HomeRouterImpl(
    private val navigator: AppNavigator
) : HomeRouter {

    override fun invoke(event: Navigation) {
        when (event) {
            is Navigation.NavigateToDetail -> navigator.navigateTo(Configuration.DetailScreen(event.id))
        }
    }
}