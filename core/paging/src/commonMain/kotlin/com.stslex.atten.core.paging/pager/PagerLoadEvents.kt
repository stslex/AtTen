package com.stslex.atten.core.paging.pager

sealed interface PagerLoadEvents {

    data class Error(
        val error: Throwable
    ) : PagerLoadEvents
}