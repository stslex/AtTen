package com.stslex.atten.core.paging.model

import com.stslex.atten.core.core.coroutine.asyncMap
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PagingResponse<out T : PagingItem>(
    @SerialName("page")
    override val page: Int,
    @SerialName("page_size")
    override val pageSize: Int,
    @SerialName("total")
    override val total: Int,
    @SerialName("has_more")
    override val hasMore: Boolean,
    @SerialName("result")
    override val result: List<T>,
) : PagingCoreData<T>

suspend fun <T : PagingItem, R : PagingItem> PagingResponse<T>.pagingResponseMap(
    transform: suspend (T) -> R,
): PagingResponse<R> = PagingResponse(
    page = page,
    pageSize = pageSize,
    total = total,
    hasMore = hasMore,
    result = result.asyncMap { transform(it) },
)