package com.stslex.atten.feature.details.navigation

import com.arkivanov.decompose.ComponentContext
import com.stslex.atten.core.ui.kit.mvi.Router
import com.stslex.atten.feature.details.ui.mvi.DetailsComponent
import com.stslex.atten.feature.details.ui.store.DetailsStoreComponent.Navigation

interface DetailsRouter : Router<Navigation>

class DetailsComponentImpl(
    private val popBack: () -> Unit,
    componentContext: ComponentContext,
) : DetailsComponent, ComponentContext by componentContext {

//    override fun invoke(event: Navigation) {
//        when (event) {
//            Navigation.Back -> navigator.popBack()
//        }
//    }
}