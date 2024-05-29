package com.stslex.atten.core.paging.model

import androidx.compose.runtime.Stable
import com.stslex.atten.core.coroutine.asyncMap
import com.stslex.atten.core.paging.model.PagingCoreData.Companion.DEFAULT_PAGE
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Stable
data class PagingState<out T : PagingUiItem>(
    override val page: Int,
    override val pageSize: Int,
    override val total: Int,
    override val hasMore: Boolean,
    override val result: ImmutableList<T>,
) : PagingCoreData<T> {

    companion object {

        fun <T : PagingUiItem> default(
            pagingConfig: PagingConfig,
        ) = PagingState(
            page = DEFAULT_PAGE,
            pageSize = pagingConfig.pageSize,
            total = 0,
            hasMore = true,
            result = persistentListOf<T>(),
        )
    }
}

suspend fun <T : PagingItem, R : PagingUiItem> PagingResponse<T>.pagingMap(
    transform: suspend (T) -> R,
): PagingState<R> = PagingState(
    page = page,
    pageSize = pageSize,
    total = total,
    hasMore = hasMore && result.isNotEmpty(),
    result = result.asyncMap { transform(it) }.toImmutableList(),
)