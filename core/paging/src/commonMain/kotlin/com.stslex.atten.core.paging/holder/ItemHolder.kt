package com.stslex.atten.core.paging.holder

import com.stslex.atten.core.paging.model.PagingItem
import kotlinx.coroutines.flow.StateFlow

interface ItemHolder<T : PagingItem> {

    val items: StateFlow<List<T>>

    fun insert(item: T)

    fun remove(uuid: Set<Any>)

    fun update(item: T)

    fun set(items: List<T>)
}

