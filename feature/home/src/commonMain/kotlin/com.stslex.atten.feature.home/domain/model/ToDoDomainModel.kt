package com.stslex.atten.feature.home.domain.model

import com.stslex.atten.core.paging.model.PagingItem

data class ToDoDomainModel(
    val id: Long,
    val title: String,
    val description: String,
    override val uniqueKey: String
) : PagingItem
