package com.stslex.atten.feature.home.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalHapticFeedback
import com.stslex.atten.core.paging.model.PagingConfig
import com.stslex.atten.core.paging.model.PagingUiState
import com.stslex.atten.core.paging.ui.PagingColumn
import com.stslex.atten.core.ui.theme.AppDimension
import com.stslex.atten.core.ui.theme.AppTheme
import com.stslex.atten.feature.home.ui.components.HomeScreenItem
import com.stslex.atten.feature.home.ui.model.TodoUiModel
import com.stslex.atten.feature.home.ui.store.HomeStoreComponent.State
import com.stslex.atten.feature.home.ui.store.ScreenState
import kotlinx.collections.immutable.toImmutableList
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun HomeScreen(
    state: State,
    onItemClicked: (id: Long) -> Unit,
    onLoadNext: () -> Unit,
    onCreateItemClick: () -> Unit,
    onDeleteItemClick: (id: Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding(),
    ) {
        PagingColumn(
            modifier = Modifier.fillMaxSize(),
            pagingState = state.paging,
            onLoadNext = onLoadNext
        ) {
            items(
                count = state.paging.items.size,
                key = state.paging.key
            ) { index ->
                state.paging.items.getOrNull(index)?.let { item ->
                    HomeScreenItem(
                        modifier = Modifier.animateItemPlacement(),
                        item = item,
                        onItemClick = onItemClicked,
                        onDeleteItemClick = onDeleteItemClick,
                    )
                }
                Spacer(modifier = Modifier.height(AppDimension.Padding.small))
            }
        }

        Button(
            onClick = { onCreateItemClick() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(AppDimension.Padding.medium),
        ) {
            Text(text = "create new item")
        }
    }
}

@Composable
@Preview
internal fun HomeScreenPreview() {
    AppTheme {
        AppTheme {
            HomeScreen(
                state = State(
                    query = "",
                    paging = PagingUiState(
                        items = Array(10) {
                            TodoUiModel(
                                id = it.toLong(),
                                title = "Title $it",
                                description = "Description $it",
                                uniqueKey = "UniqueKey $it"
                            )
                        }.toList().toImmutableList(),
                        hasMore = true,
                        total = 100,
                        config = PagingConfig.DEFAULT
                    ),
                    screen = ScreenState.Content.Data
                ),
                onLoadNext = {},
                onItemClicked = {},
                onDeleteItemClick = {},
                onCreateItemClick = {}
            )
        }
    }
}