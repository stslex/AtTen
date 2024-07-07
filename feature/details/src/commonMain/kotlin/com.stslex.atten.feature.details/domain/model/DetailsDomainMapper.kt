package com.stslex.atten.feature.details.domain.model

import com.stslex.atten.core.todo.data.model.ToDoDataModel
import com.stslex.atten.core.todo.data.model.UpdateTodoDataModel

fun ToDoDataModel.toDomain() = ToDoDetailsDomainModel(
    uuid = uuid,
    title = title,
    description = description,
    updatedAt = updatedAt,
    createdAt = createdAt
)

fun ToDoDomainUpdateModel.toDataModel() = UpdateTodoDataModel(
    uuid = uuid,
    title = title,
    description = description,
    createdAt = createdAt
)