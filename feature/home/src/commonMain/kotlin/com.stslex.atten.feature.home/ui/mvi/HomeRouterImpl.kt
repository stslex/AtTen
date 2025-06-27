package com.stslex.atten.feature.home.ui.mvi

import com.arkivanov.decompose.ComponentContext
import com.stslex.atten.core.ui.navigation.Component
import com.stslex.atten.feature.home.ui.store.HomeStoreComponent

class HomeComponentImpl(
    private val popBack: () -> Unit,
    private val navTo: () -> Unit,
    componentContext: ComponentContext
) : HomeComponent, ComponentContext by componentContext {

//    override fun invoke(event: HomeStoreComponent.Navigation) {
//        when (event) {
//            is HomeStoreComponent.Navigation.NavigateToDetail -> navigator.navigateTo(
//                Component.DetailScreen(
//                    event.id
//                )
//            )
//
//            is HomeStoreComponent.Navigation.Back -> navigator.popBack()
//        }
//    }
}