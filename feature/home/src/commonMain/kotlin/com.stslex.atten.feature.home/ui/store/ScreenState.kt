package com.stslex.atten.feature.home.ui.store

import androidx.compose.runtime.Stable

sealed interface ScreenState {

    @Stable
    sealed interface Content : ScreenState {

        @Stable
        data object Data : Content

        @Stable
        data object Loading : Content

        @Stable
        data object Refresh : Content
    }

    @Stable
    data object Shimmer : ScreenState

    @Stable
    data object Empty : Content

    @Stable
    data class Error(val message: String) : ScreenState
}