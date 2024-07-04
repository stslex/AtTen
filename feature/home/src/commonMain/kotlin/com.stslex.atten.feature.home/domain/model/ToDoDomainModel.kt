package com.stslex.atten.feature.home.domain.model

import com.stslex.atten.core.paging.PagingItem

data class ToDoDomainModel(
    val uuid: String,
    val title: String,
    val description: String,
) : PagingItem

data class CreateTodoDomainModel(
    val title: String,
    val description: String
)
