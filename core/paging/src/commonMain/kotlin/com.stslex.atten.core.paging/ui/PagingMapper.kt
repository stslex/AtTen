package com.stslex.atten.core.paging.ui

import com.stslex.atten.core.paging.model.PagingItem
import com.stslex.atten.core.paging.model.PagingUiItem

fun interface PagingMapper<T : PagingItem, R : PagingUiItem> {

    suspend operator fun invoke(item: T): R
}