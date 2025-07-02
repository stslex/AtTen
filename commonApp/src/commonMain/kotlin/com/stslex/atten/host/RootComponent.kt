package com.stslex.atten.host

import com.arkivanov.decompose.Cancellation
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.stslex.atten.core.ui.navigation.Config
import com.stslex.atten.feature.details.ui.mvi.DetailsComponent
import com.stslex.atten.feature.home.ui.mvi.HomeComponent

interface RootComponent {

    val stack: Value<ChildStack<Config, Child>>

    fun onConfigChanged(block: (Config) -> Unit): Cancellation

    sealed interface Child {

        data class Home(val component: HomeComponent) : Child

        data class Details(val component: DetailsComponent) : Child
    }

}
