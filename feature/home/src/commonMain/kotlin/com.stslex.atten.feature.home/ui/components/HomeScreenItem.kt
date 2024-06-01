package com.stslex.atten.feature.home.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.stslex.atten.core.ui.components.CardWithAnimatedBorder
import com.stslex.atten.core.ui.theme.AppDimension
import com.stslex.atten.core.ui.theme.AppTheme
import com.stslex.atten.feature.home.ui.model.TodoUiModel

@Composable
internal fun HomeScreenItem(
    item: TodoUiModel,
    onItemClick: (id: String) -> Unit,
    onItemLongClick: (id: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    CardWithAnimatedBorder(
        modifier = modifier
            .width(IntrinsicSize.Max)
            .padding(AppDimension.Padding.medium),
        onLongClick = {
            onItemLongClick(item.uuid)
        },
        onClick = {
            onItemClick(item.uuid)
        },
        isAnimated = item.isSelected,
        borderSize = 2.dp,
        disableBorderColor = Color.Transparent,
    ) {
        Column(
            modifier = Modifier
                .padding(AppDimension.Padding.big),
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(AppDimension.Padding.small))
            Text(
                text = item.description + "item ${item.uuid}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
internal fun HomeScreenItemPreview() {
    AppTheme {
        HomeScreenItem(
            item = TodoUiModel(
                uuid = "uniqueKey1",
                title = "Title",
                description = "Description",
                isSelected = false
            ),
            onItemClick = {},
            onItemLongClick = {}
        )
    }
}