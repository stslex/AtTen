package com.stslex.atten.core.paging.states

import com.stslex.atten.core.paging.model.PagingUiItem
import com.stslex.atten.core.paging.model.PagingConfig
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlin.math.roundToInt

data class PagingUiState<out T : PagingUiItem>(
    val items: ImmutableList<T>,
    val hasMore: Boolean,
    val total: Int,
    val config: PagingConfig
) {

    val pageOffset: Int = (config.pageSize * config.pageOffset).roundToInt()

    val key: ((index: Int) -> Any)? = if (items.isEmpty()) null else { index ->
        checkNotNull(items.getOrNull(index)) {
            "Index out of bounds: $index, size: ${items.size}"
        }.uuid
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
    items = result.toImmutableList(),
    hasMore = hasMore,
    total = total,
    config = pagingConfig
)