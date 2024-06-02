package com.stslex.atten.core.navigation.decompose

internal sealed class Child<T : AppComponents>(
    open val component: T
) {

    data class HomeScreen(
        override val component: AppComponents.HomeComponent
    ) : Child<AppComponents.HomeComponent>(component)

    data class DetailScreen(
        override val component: AppComponents.DetailComponent
    ) : Child<AppComponents.DetailComponent>(component)
}