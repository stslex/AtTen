package com.stslex.atten.feature.home.ui.model

import androidx.compose.runtime.Stable
import com.stslex.atten.core.paging.PagingItem

@Stable
data class TodoUiModel(
    val uuid: String,
    val title: String,
    val description: String,
    val isSelected: Boolean,
) : PagingItem
