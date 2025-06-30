package com.stslex.atten.core.paging.factory

import com.stslex.atten.core.core.coroutine.scope.AppCoroutineScope
import com.stslex.atten.core.paging.model.PagingItem
import com.stslex.atten.core.paging.model.PagingResponse
import com.stslex.atten.core.paging.holder.ItemHolder
import com.stslex.atten.core.paging.model.PagingConfig
import com.stslex.atten.core.paging.pager.Pager

interface PagerFactory {

    fun <T : PagingItem> create(
        pagingConfig: PagingConfig = PagingConfig.DEFAULT,
        scope: AppCoroutineScope,
        holder: ItemHolder<T>,
        request: suspend (page: Int, pageSize: Int) -> PagingResponse<T>,
    ): Pager<T>
}

