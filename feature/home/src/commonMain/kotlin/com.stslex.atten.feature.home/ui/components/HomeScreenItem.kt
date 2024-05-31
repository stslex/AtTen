package com.stslex.atten.feature.home.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppDimension.Padding.medium),
    ) {
        Card(
            modifier = modifier
                .width(IntrinsicSize.Max)
                .padding(AppDimension.Padding.medium),
            onClick = { onItemClick(item.id) },
            shape = CardDefaults.outlinedShape,
            border = CardDefaults.outlinedCardBorder(),
            elevation = CardDefaults.elevatedCardElevation(),
            colors = CardDefaults.elevatedCardColors().copy(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(AppDimension.Padding.big)
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(AppDimension.Padding.small))
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
        Spacer(modifier = Modifier.width(AppDimension.Padding.medium))
        Button(
            modifier = Modifier.align(Alignment.CenterVertically),
            onClick = { onDeleteItemClick(item.id) }
        ) {
            Text(text = "Remove")
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