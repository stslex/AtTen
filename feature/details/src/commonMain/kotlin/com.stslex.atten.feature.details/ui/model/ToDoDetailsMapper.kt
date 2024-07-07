package com.stslex.atten.feature.details.ui.model

import com.stslex.atten.feature.details.domain.model.ToDoDetailsDomainModel
import com.stslex.atten.feature.details.domain.model.ToDoDomainUpdateModel
import kotlinx.datetime.LocalDateTime

fun ToDoDetailsDomainModel.toUi() = ToDoDetailsUIModel(
    uuid = uuid,
    title = title,
    description = description,
)

fun ToDoDetailsUIModel.toDomain(
    createdAt: LocalDateTime?
): ToDoDomainUpdateModel? {
    if (createdAt == null) return null
    return ToDoDomainUpdateModel(
        uuid = uuid,
        title = title,
        description = description,
        createdAt = createdAt
    )
}