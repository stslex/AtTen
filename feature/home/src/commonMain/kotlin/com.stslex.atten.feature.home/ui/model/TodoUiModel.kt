package com.stslex.atten.feature.home.ui.model

import androidx.compose.runtime.Stable
import com.stslex.atten.core.paging.model.PagingUiItem

@Stable
data class TodoUiModel(
    override val uuid: String,
    val title: String,
    val description: String,
    val isSelected: Boolean,
) : PagingUiItem
