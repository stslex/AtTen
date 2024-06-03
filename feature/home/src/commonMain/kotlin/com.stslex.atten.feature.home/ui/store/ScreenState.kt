package com.stslex.atten.feature.home.ui.store

import androidx.compose.runtime.Stable
import com.stslex.atten.core.paging.states.PagerLoadState

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

fun PagerLoadState.toUi() = when (this) {
    PagerLoadState.Data -> ScreenState.Content.Data
    PagerLoadState.Empty -> ScreenState.Empty
    is PagerLoadState.Error -> ScreenState.Error(error.message ?: "Unknown error")
    PagerLoadState.Initial -> ScreenState.Shimmer
    PagerLoadState.Loading -> ScreenState.Content.Loading
    PagerLoadState.Refresh -> ScreenState.Content.Refresh
    PagerLoadState.Retry -> ScreenState.Shimmer
}