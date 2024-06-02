package com.stslex.atten.feature.home.domain.model

import com.stslex.atten.core.paging.model.PagingItem

data class ToDoDomainModel(
    override val uuid: String,
    val title: String,
    val description: String,
) : PagingItem

data class CreateTodoDomainModel(
    val title: String,
    val description: String
)
