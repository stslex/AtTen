package com.stslex.atten.core.ui.navigation

import com.arkivanov.decompose.ComponentContext

interface Router : ComponentContext {

    fun navTo(config: Config)

    fun popBack()

}