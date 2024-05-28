package com.stslex.atten.core.paging.pager

import com.stslex.atten.core.paging.model.PagingState
import com.stslex.atten.core.paging.model.PagingUiItem
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface StorePager<out Item : PagingUiItem> {

    val state: StateFlow<PagingState<Item>>

    val loadState: StateFlow<PagerLoadState>

    val loadEvents: SharedFlow<PagerLoadEvents>

    fun initialLoad()

    fun load()

    fun refresh(isForceLoad: Boolean)

    fun retry()
}