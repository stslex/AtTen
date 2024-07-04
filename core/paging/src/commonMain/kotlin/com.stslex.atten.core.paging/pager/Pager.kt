package com.stslex.atten.core.paging.pager

import com.stslex.atten.core.paging.model.PagingItem
import com.stslex.atten.core.paging.states.PagerLoadEvents
import com.stslex.atten.core.paging.states.PagerLoadState
import com.stslex.atten.core.paging.states.PagerAction
import com.stslex.atten.core.paging.states.PagingState
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface Pager<T : PagingItem> {

    val state: StateFlow<PagingState<T>>

    val loadState: StateFlow<PagerLoadState>

    val loadEvents: SharedFlow<PagerLoadEvents>

    fun process(action: PagerAction)
}
