package com.stslex.atten.feature.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import com.stslex.atten.core.ui.theme.AppDimension
import com.stslex.atten.feature.home.ui.store.HomeStoreComponent.State
import com.stslex.atten.feature.home.ui.store.HomeStoreComponent.State.Companion.PAGE_SIZE
import com.stslex.atten.feature.home.ui.store.ScreenState
import com.stslex.atten.feature.home.ui.store.TodoUiModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull

@Composable
internal fun HomeScreen(
    state: State,
    onItemClicked: (id: Long) -> Unit,
    onLoadNext: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val lazyListState = rememberLazyListState()
    LaunchedEffect(lazyListState, state.items) {
        snapshotFlow {
            lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        }
            .filterNotNull()
            .filter { index ->
                // todo 10 - page size - replace  to paging item
                val hasMore = state.items.size % PAGE_SIZE == 0
                hasMore &&
                        index >= (state.items.size - PAGE_SIZE * 0.5) &&
                        state.state is ScreenState.Content
            }
            .distinctUntilChanged()
            .collect {
                onLoadNext()
            }
    }
    LazyColumn(
        modifier = modifier,
        state = lazyListState,
    ) {
        items(
            count = state.items.size,
            key = { index -> state.items[index].id }
        ) { index ->
            state.items.getOrNull(index)?.let { item ->
                HomeScreenItem(
                    item = item,
                    onItemClick = onItemClicked,
                )
            }
            Spacer(modifier = Modifier.height(AppDimension.Padding.small))
        }
    }
}

@Composable
internal fun HomeScreenItem(
    item: TodoUiModel,
    onItemClick: (id: Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedCard(
        modifier = modifier.fillMaxWidth()
            .padding(AppDimension.Padding.medium),
        onClick = { onItemClick(item.id) },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppDimension.Padding.medium),
        ) {
            Text(
                text = item.title,
            )
            Spacer(modifier = Modifier.height(AppDimension.Padding.small))
        }
    }
}
