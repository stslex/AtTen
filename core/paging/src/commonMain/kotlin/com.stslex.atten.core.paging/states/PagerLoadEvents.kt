package com.stslex.atten.core.paging.states

sealed interface PagerLoadEvents {

    data class Error(
        val error: Throwable
    ) : PagerLoadEvents
}