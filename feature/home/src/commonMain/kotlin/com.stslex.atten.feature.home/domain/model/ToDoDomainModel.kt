package com.stslex.atten.feature.home.domain.model

import com.stslex.atten.core.paging.model.PagingItem
import kotlinx.datetime.LocalDateTime

data class ToDoDomainModel(
    override val uuid: String,
    val title: String,
    val description: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) : PagingItem

data class CreateTodoDomainModel(
    val title: String,
    val description: String
)
