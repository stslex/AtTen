package com.stslex.atten.feature.details.domain.model

import com.stslex.atten.core.todo.data.model.ToDoDataModel

fun ToDoDataModel.toDomain() = ToDoDetailsDomainModel(
    uuid = uuid,
    title = title,
    description = description,
)

fun ToDoDetailsDomainModel.toDataModel(
    dataModel: ToDoDataModel
) = ToDoDataModel(
    uuid = uuid,
    title = title,
    description = description,
    createdAt = dataModel.createdAt,
    updatedAt = dataModel.updatedAt,
)