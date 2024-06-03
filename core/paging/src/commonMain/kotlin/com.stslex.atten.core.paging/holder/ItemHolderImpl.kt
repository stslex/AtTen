package com.stslex.atten.core.paging.holder

import com.stslex.atten.core.paging.model.PagingItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ItemHolderImpl<T : PagingItem> : ItemHolder<T> {

    private val _items = MutableStateFlow<List<T>>(emptyList())
    override val items: StateFlow<List<T>> = _items.asStateFlow()

    override fun insert(item: T) {
        _items.value += item
    }

    override fun remove(uuid: Set<Any>) {
        _items.value = _items.value.filterNot {
            uuid.contains(it.uuid)
        }
    }

    override fun update(item: T) {
        _items.value = _items.value.map { if (it.uuid == item.uuid) item else it }
    }

    override fun set(items: List<T>) {
        _items.value = items
    }
}