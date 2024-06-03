package com.stslex.atten.core.paging.states

import com.stslex.atten.core.coroutine.asyncMap
import com.stslex.atten.core.paging.model.PagingItem
import com.stslex.atten.core.paging.model.PagingResponse
import com.stslex.atten.core.paging.model.PagingConfig

data class PagingState<T : PagingItem>(
    val page: Int,
    val pageSize: Int,
    val total: Int,
    val hasMore: Boolean,
    val result: List<T>,
) {

    companion object {

        fun <T : PagingItem> default(
            pagingConfig: PagingConfig
        ): PagingState<T> = PagingState(
            page = 0,
            pageSize = pagingConfig.pageSize,
            total = 0,
            hasMore = true,
            result = emptyList(),
        )
    }
}

fun <T : PagingItem> PagingResponse<T>.pagingMap(): PagingState<T> = PagingState(
    page = page,
    pageSize = pageSize,
    total = total,
    hasMore = hasMore && result.isNotEmpty(),
    result = result,
)

suspend fun <T : PagingItem, R : PagingItem> PagingState<T>.pagingMap(
    transform: (T) -> R
): PagingState<R> = PagingState(
    page = page,
    pageSize = pageSize,
    total = total,
    hasMore = hasMore,
    result = result.asyncMap { transform(it) },
)
