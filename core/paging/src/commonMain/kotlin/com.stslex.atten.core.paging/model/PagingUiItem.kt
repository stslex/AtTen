package com.stslex.atten.core.paging.model

import androidx.compose.runtime.Stable

@Stable
interface PagingUiItem : PagingItem {
    override val uuid: String
}
