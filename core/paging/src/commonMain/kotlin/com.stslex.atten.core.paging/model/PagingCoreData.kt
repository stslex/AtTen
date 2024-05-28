package com.stslex.atten.core.paging.model

interface PagingCoreData<out T : PagingItem> {
    val page: Int
    val pageSize: Int
    val total: Int
    val hasMore: Boolean
    val result: List<T>

    companion object {

        const val DEFAULT_PAGE_SIZE = 15
        const val DEFAULT_PAGE = 0
        const val DEFAULT_PAGE_OFFSET = 0.5f
        const val DEFAULT_PAGING_TYPE = "ItemPaging"
        const val DEFAULT_APPEND_TYPE = "AppendPaging"
        const val DEFAULT_BOTTOM_TYPE = "BottomPaging"
        const val DEFAULT_PAGING_DELAY = 300L
        const val DEFAULT_PAGING_LOAD_SIZE = 3
        const val DEFAULT_QUERY_LOAD_SIZE = 2
    }
}