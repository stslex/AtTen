package com.stslex.atten.feature.details.navigation

import com.stslex.atten.core.ui.navigation.Navigator
import com.stslex.atten.core.ui.kit.mvi.Router
import com.stslex.atten.feature.details.ui.store.DetailsStoreComponent.Navigation

interface DetailsRouter : Router<Navigation>

class DetailsRouterImpl(
    private val navigator: Navigator
) : DetailsRouter {

    override fun invoke(event: Navigation) {
        when (event) {
            Navigation.Back -> navigator.popBack()
        }
    }
}