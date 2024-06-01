package com.stslex.atten.feature.details.ui.model

import com.stslex.atten.feature.details.domain.model.ToDoDetailsDomainModel

fun ToDoDetailsDomainModel.toUi() = ToDoDetailsUIModel(
    uuid = uuid,
    title = title,
    description = description,
)

fun ToDoDetailsUIModel.toDomain() = ToDoDetailsDomainModel(
    uuid = uuid,
    title = title,
    description = description,
)