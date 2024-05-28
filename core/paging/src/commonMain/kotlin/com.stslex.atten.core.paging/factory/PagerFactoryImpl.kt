package com.stslex.atten.core.paging.factory

import com.stslex.atten.core.coroutine.scope.AppCoroutineScope
import com.stslex.atten.core.paging.model.PagingConfig
import com.stslex.atten.core.paging.model.PagingItem
import com.stslex.atten.core.paging.model.PagingResponse
import com.stslex.atten.core.paging.model.PagingUiItem
import com.stslex.atten.core.paging.pager.StorePager
import com.stslex.atten.core.paging.pager.StorePagerImpl
import com.stslex.atten.core.paging.ui.PagingMapper
import com.stslex.atten.core.paging.worker.PagingWorkerImpl

class PagerFactoryImpl : PagerFactory {

    override fun <T : PagingUiItem, R : PagingItem> create(
        scope: AppCoroutineScope,
        request: suspend (page: Int, pageSize: Int) -> PagingResponse<R>,
        mapper: PagingMapper<R, T>,
        config: PagingConfig
    ): StorePager<T> = StorePagerImpl(
        pagingWorker = PagingWorkerImpl(
            scope = scope,
            delay = config.delay,
            defaultLoadSize = config.defaultLoadSize,
            queryLoadSize = config.queryLoadSize
        ),
        request = request,
        mapper = mapper,
        pagingConfig = config
    )
}