package com.stslex.atten.core.paging.holder

import com.stslex.atten.core.paging.model.PagingItem
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface ItemHolder<T : PagingItem> {

    val items: StateFlow<List<T>>

    val event: SharedFlow<ItemLoaderEvent<T>>

    suspend fun create(item: T)

    suspend fun insert(item: List<T>)

    suspend fun remove(uuid: Set<Any>)

    suspend fun updateAndReplace(item: T)

    suspend fun replace(items: List<T>)
}

sealed interface ItemLoaderEvent<T : PagingItem> {

    data class Create<T : PagingItem>(val item: T) : ItemLoaderEvent<T>

    data class Insert<T : PagingItem>(val items: List<T>) : ItemLoaderEvent<T>

    data class Remove<T : PagingItem>(val uuid: Set<Any>) : ItemLoaderEvent<T>

    data class Update<T : PagingItem>(val item: T) : ItemLoaderEvent<T>

    data class Replace<T : PagingItem>(val items: List<T>) : ItemLoaderEvent<T>
}

