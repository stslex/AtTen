package com.stslex.atten.core.navigation.decompose

import com.arkivanov.decompose.ComponentContext

internal sealed class AppComponents(
    context: ComponentContext
) : ComponentContext by context {

    data class HomeComponent(
        private val context: ComponentContext,
    ) : AppComponents(context)

    data class DetailComponent(
        val id: String,
        private val context: ComponentContext,
    ) : AppComponents(context)
}