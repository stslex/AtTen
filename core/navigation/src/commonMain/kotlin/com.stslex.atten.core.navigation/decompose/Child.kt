package com.stslex.atten.core.navigation.decompose

internal sealed interface Child {

    data class HomeScreen(
        val component: AppComponents.HomeComponent
    ) : Child

    data class DetailScreen(
        val component: AppComponents.DetailComponent
    ) : Child
}