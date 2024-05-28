package com.stslex.atten.feature.home.ui.model

import androidx.compose.runtime.Stable
import com.stslex.atten.core.paging.model.PagingUiItem

@Stable
data class TodoUiModel(
    val id: Long,
    val title: String,
    val description: String,
    override val uniqueKey: String
) : PagingUiItem
