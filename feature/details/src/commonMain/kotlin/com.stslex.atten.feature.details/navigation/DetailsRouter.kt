package com.stslex.atten.feature.details.navigation

import com.stslex.atten.core.navigation.navigator.AppNavigator
import com.stslex.atten.core.ui.mvi.Router
import com.stslex.atten.feature.details.ui.store.DetailsStoreComponent.Navigation

interface DetailsRouter : Router<Navigation>

class DetailsRouterImpl(
    private val navigator: AppNavigator
) : DetailsRouter {

    override fun invoke(event: Navigation) {
        when (event) {
            Navigation.Back -> navigator.popBack()
        }
    }
}