package com.stslex.atten.core.navigation.navigator

sealed interface NavigationTarget {

    data object PopBack : NavigationTarget

    interface Screen : NavigationTarget {

        val route: String

        val screenRoute: String

        val isSingleTop: Boolean
    }
}