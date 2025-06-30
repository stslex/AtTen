package com.stslex.atten.core.paging.holder

import com.stslex.atten.core.paging.model.PagingItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

open class ItemHolderImpl<T : PagingItem> : ItemHolder<T> {

    private val _items = MutableStateFlow<List<T>>(emptyList())
    override val items: StateFlow<List<T>> = _items.asStateFlow()

    private val _event = MutableSharedFlow<ItemLoaderEvent<T>>()
    override val event: SharedFlow<ItemLoaderEvent<T>> = _event.asSharedFlow()

    override suspend fun create(item: T) {
        _event.emit(ItemLoaderEvent.Create(item))
        _items.value = listOf(item) + _items.value
    }

    override suspend fun insert(item: List<T>) {
        _event.emit(ItemLoaderEvent.Insert(item))
        _items.value += item
    }

    override suspend fun remove(uuid: Set<Any>) {
        if (items.value.any { it.uuid == uuid }) {
            throw IllegalArgumentException("Item with uuid $uuid not found")
        }
        _event.emit(ItemLoaderEvent.Remove(uuid))
        _items.value = _items.value.filterNot {
            uuid.contains(it.uuid)
        }
    }

    override suspend fun updateAndReplace(item: T) {
        val newItems = items.value.filterNot { it.uuid == item.uuid }
        _items.value = listOf(item) + newItems
        _event.emit(ItemLoaderEvent.Update(item))
    }

    override suspend fun replace(items: List<T>) {
        _event.emit(ItemLoaderEvent.Replace(items))
        _items.value = items
    }
}