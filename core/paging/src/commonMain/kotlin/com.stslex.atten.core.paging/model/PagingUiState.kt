package com.stslex.atten.core.paging.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlin.math.roundToInt

data class PagingUiState<out T : PagingUiItem>(
    val items: ImmutableList<T>,
    val hasMore: Boolean,
    val total: Int,
    val config: PagingConfig
) {

    val pageOffset: Int = (config.pageSize * config.pageOffset).roundToInt()

    val key: ((index: Int) -> Any)? = if (items.isEmpty()) null else { index ->
        items[index].uniqueKey
    }

    companion object {

        fun <T : PagingUiItem> default(config: PagingConfig) = PagingUiState(
            items = persistentListOf<T>(),
            total = 0,
            hasMore = true,
            config = config
        )
    }
}

fun <T : PagingUiItem> PagingState<T>.toUi(
    pagingConfig: PagingConfig
): PagingUiState<T> = PagingUiState(
    items = result,
    hasMore = hasMore,
    total = total,
    config = pagingConfig
)