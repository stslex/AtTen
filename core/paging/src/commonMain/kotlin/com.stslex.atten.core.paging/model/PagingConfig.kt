package com.stslex.atten.core.paging.model

data class PagingConfig(
    val pageSize: Int,
    val pageOffset: Float = PagingCoreData.DEFAULT_PAGE_OFFSET,
    val delay: Long = PagingCoreData.DEFAULT_PAGING_DELAY,
    val defaultLoadSize: Int = PagingCoreData.DEFAULT_PAGING_LOAD_SIZE,
    val queryLoadSize: Int = PagingCoreData.DEFAULT_QUERY_LOAD_SIZE
) {

    companion object {

        val DEFAULT = PagingConfig(
            pageSize = PagingCoreData.DEFAULT_PAGE_SIZE,
            pageOffset = PagingCoreData.DEFAULT_PAGE_OFFSET,
            delay = PagingCoreData.DEFAULT_PAGING_DELAY,
            defaultLoadSize = PagingCoreData.DEFAULT_PAGING_LOAD_SIZE,
            queryLoadSize = PagingCoreData.DEFAULT_QUERY_LOAD_SIZE
        )
    }
}