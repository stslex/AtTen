package com.stslex.atten.feature.details.ui.mvi

import androidx.compose.runtime.Stable

@Stable
sealed interface ScreenState {

    @Stable
    data object Shimmer : ScreenState

    @Stable
    data object Content : ScreenState

    @Stable
    data class Error(val message: String) : ScreenState
}