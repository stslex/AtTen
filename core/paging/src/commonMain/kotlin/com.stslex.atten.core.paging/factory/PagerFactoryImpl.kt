package com.stslex.atten.core.paging.factory

import com.stslex.atten.core.coroutine.scope.AppCoroutineScope
import com.stslex.atten.core.paging.holder.ItemHolder
import com.stslex.atten.core.paging.model.PagingConfig
import com.stslex.atten.core.paging.model.PagingItem
import com.stslex.atten.core.paging.model.PagingResponse
import com.stslex.atten.core.paging.pager.Pager
import com.stslex.atten.core.paging.pager.PagerImpl
import com.stslex.atten.core.paging.worker.PagingWorkerImpl
import org.koin.core.annotation.Single

@Single
class PagerFactoryImpl : PagerFactory {

    override fun <T : PagingItem> create(
        pagingConfig: PagingConfig,
        scope: AppCoroutineScope,
        holder: ItemHolder<T>,
        request: suspend (page: Int, pageSize: Int) -> PagingResponse<T>,
    ): Pager<T> = PagerImpl(
        pagingConfig = pagingConfig,
        pagingWorker = PagingWorkerImpl(
            scope = scope,
            delay = pagingConfig.delay,
            defaultLoadSize = pagingConfig.defaultLoadSize,
            queryLoadSize = pagingConfig.queryLoadSize,
        ),
        request = request,
        scope = scope,
        holder = holder,
    )
}