package com.stslex.atten.core.paging.pager

import com.stslex.atten.core.paging.model.PagingState
import com.stslex.atten.core.paging.model.PagingUiItem
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface StorePager<Item : PagingUiItem> {

    val state: StateFlow<PagingState<Item>>

    val loadState: StateFlow<PagerLoadState>

    val loadEvents: SharedFlow<PagerLoadEvents>

    fun initialLoad()

    fun load(isForceLoad: Boolean = false)

    fun refresh(isForceLoad: Boolean)

    fun retry()

    fun itemRemoved(uniqueKey: Any)

    fun itemInserted(item: Item)
}