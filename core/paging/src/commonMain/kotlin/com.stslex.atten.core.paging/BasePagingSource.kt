package com.stslex.atten.core.paging

import app.cash.paging.PagingSource
import app.cash.paging.PagingSourceLoadParams
import app.cash.paging.PagingSourceLoadResult
import app.cash.paging.PagingSourceLoadResultError
import app.cash.paging.PagingSourceLoadResultPage
import app.cash.paging.PagingState

abstract class BasePagingSource<Value : PagingItem> : PagingSource<Int, Value>() {

    abstract suspend fun loadData(page: Int, pageSize: Int): PagingResponse<Value>

    override suspend fun load(params: PagingSourceLoadParams<Int>): PagingSourceLoadResult<Int, Value> {
        val currentPage = params.key ?: 1
        val limit = params.loadSize
        return try {
            val response = loadData(currentPage, limit)
            PagingSourceLoadResultPage(
                data = response.items,
                prevKey = currentPage.dec().takeIf { currentPage > 1 },
                nextKey = currentPage.inc().takeIf { response.items.lastIndex >= currentPage }
            )

        } catch (e: Exception) {
            PagingSourceLoadResultError(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Value>): Int? {
        return state.anchorPosition
    }
}