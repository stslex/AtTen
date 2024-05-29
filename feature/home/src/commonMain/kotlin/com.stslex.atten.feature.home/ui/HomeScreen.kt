package com.stslex.atten.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.stslex.atten.core.paging.ui.PagingColumn
import com.stslex.atten.core.ui.theme.AppDimension
import com.stslex.atten.feature.home.ui.model.TodoUiModel
import com.stslex.atten.feature.home.ui.store.HomeStoreComponent.State

@Composable
internal fun HomeScreen(
    state: State,
    onItemClicked: (id: Long) -> Unit,
    onLoadNext: () -> Unit,
    modifier: Modifier = Modifier,
) {
    PagingColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding(),
        pagingState = state.paging,
        onLoadNext = onLoadNext
    ) {
        item {
            Button(
                onClick = { onItemClicked(1) },
                modifier = Modifier.padding(AppDimension.Padding.medium),
            ) {
                Text(text = "Nav to 1")
            }
        }
        items(
            count = state.paging.items.size,
            key = state.paging.key
        ) { index ->
            state.paging.items.getOrNull(index)?.let { item ->
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
