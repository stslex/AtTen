package com.stslex.atten.core.paging.pager

sealed interface PagerLoadState {

    data object Data : PagerLoadState

    data object Initial : PagerLoadState

    data object Loading : PagerLoadState

    data object Refresh : PagerLoadState

    data object Retry : PagerLoadState

    data class Error(val error: Throwable) : PagerLoadState

    data object Empty : PagerLoadState
}
