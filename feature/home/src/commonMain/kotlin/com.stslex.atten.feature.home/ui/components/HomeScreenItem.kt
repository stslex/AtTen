package com.stslex.atten.feature.home.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.stslex.atten.core.ui.theme.AppDimension
import com.stslex.atten.core.ui.theme.AppTheme
import com.stslex.atten.feature.home.ui.model.TodoUiModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun HomeScreenItem(
    item: TodoUiModel,
    onItemClick: (id: String) -> Unit,
    onItemLongClick: (id: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val cardColor by animateColorAsState(
        targetValue = if (item.isSelected) {
            MaterialTheme.colorScheme.surfaceContainerLowest
        } else {
            MaterialTheme.colorScheme.surfaceContainerHighest
        }
    )
    val textCardColor by animateColorAsState(
        targetValue = if (item.isSelected) {
            MaterialTheme.colorScheme.onSurfaceVariant
        } else {
            MaterialTheme.colorScheme.onSurface
        }
    )
    val paddingSize by animateDpAsState(
        targetValue = if (item.isSelected) {
            AppDimension.Padding.big
        } else {
            AppDimension.Padding.medium
        }
    )

    val borderBrushStart by animateColorAsState(
        targetValue = if (item.isSelected) {
            Color.Red.copy(alpha = 0.7f)
        } else {
            MaterialTheme.colorScheme.onSurface
        }
    )
    val borderBrushMiddle by animateColorAsState(
        targetValue = if (item.isSelected) {
            Color.Blue.copy(alpha = 0.7f)
        } else {
            MaterialTheme.colorScheme.onSurface
        }
    )
    val borderBrushEnd by animateColorAsState(
        targetValue = if (item.isSelected) {
            Color.Green.copy(alpha = 0.7f)
        } else {
            MaterialTheme.colorScheme.onSurface
        }
    )

    val borderBrushWidth by animateDpAsState(
        targetValue = if (item.isSelected) {
            3.dp
        } else {
            1.dp
        }
    )


    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(AppDimension.Padding.medium),
    ) {
        Column(
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .weight(1f)
                .padding(AppDimension.Padding.medium)
                .clip(CardDefaults.outlinedShape)
                .border(
                    width = borderBrushWidth,
                    brush = Brush.sweepGradient(
                        colors = listOf(
                            borderBrushStart,
                            borderBrushMiddle,
                            borderBrushEnd
                        )
                    ),
                    shape = CardDefaults.outlinedShape
                )
                .background(color = cardColor)
                .combinedClickable(
                    onLongClick = {
                        onItemLongClick(item.uuid)
                    },
                    onClick = {
                        if (item.isSelected) {
                            onItemLongClick(item.uuid)
                        } else {
                            onItemClick(item.uuid)
                        }
                    }
                )
                .padding(paddingSize),
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.headlineMedium,
                color = textCardColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(AppDimension.Padding.small))
            Text(
                text = item.description + "item ${item.uuid}",
                style = MaterialTheme.typography.titleMedium,
                color = textCardColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.CenterVertically),
            visible = item.isSelected,
            enter = fadeIn() + expandHorizontally() + scaleIn(),
            exit = fadeOut() + shrinkHorizontally() + scaleOut(),
        ) {
            Spacer(modifier = Modifier.width(AppDimension.Padding.medium))
            IconButton(
                onClick = { onItemLongClick(item.uuid) }
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Select item"
                )
            }
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