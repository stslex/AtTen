package com.stslex.atten.feature.home.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.stslex.atten.core.ui.theme.AppDimension
import com.stslex.atten.core.ui.theme.AppTheme
import com.stslex.atten.feature.home.ui.model.TodoUiModel

@Composable
internal fun HomeScreenItem(
    item: TodoUiModel,
    onItemClick: (id: Long) -> Unit,
    onDeleteItemClick: (id: Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(AppDimension.Padding.medium),
        onClick = { onItemClick(item.id) },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppDimension.Padding.medium),
        ) {
            Column {
                Text(
                    text = item.title,
                )
                Spacer(modifier = Modifier.height(AppDimension.Padding.small))
            }
            Spacer(modifier = Modifier.width(AppDimension.Padding.small))
            Button(
                modifier = Modifier.alignByBaseline(),
                onClick = { onDeleteItemClick(item.id) }
            ) {
                Text(text = "Remove")
            }
        }
    }
}

@Composable
internal fun HomeScreenItemPreview() {
    AppTheme {
        HomeScreenItem(
            item = TodoUiModel(
                id = 1,
                title = "Title",
                description = "Description",
                uniqueKey = "uniqueKey",
            ),
            onItemClick = {},
            onDeleteItemClick = {},
        )
    }
}