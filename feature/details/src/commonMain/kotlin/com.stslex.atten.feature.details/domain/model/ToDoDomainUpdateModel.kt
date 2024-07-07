package com.stslex.atten.feature.details.domain.model

import kotlinx.datetime.LocalDateTime

data class ToDoDomainUpdateModel(
    val uuid: String,
    val title: String,
    val description: String,
    val createdAt: LocalDateTime,
)
