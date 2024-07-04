package com.stslex.atten.core.paging.states

sealed interface PagerAction {

    data object Initial : PagerAction
    data class Load(val isForce: Boolean) : PagerAction
    data object Retry : PagerAction
    data class Refresh(val isForce: Boolean) : PagerAction
}