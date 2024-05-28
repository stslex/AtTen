package com.stslex.atten.feature.home.ui.store

sealed interface ScreenState {

    data object Shimmer : ScreenState

    sealed interface Content : ScreenState {

        data object Loaded : Content

        data object Empty : Content

        data object Append : Content
    }

    data class Error(val message: String) : ScreenState
}