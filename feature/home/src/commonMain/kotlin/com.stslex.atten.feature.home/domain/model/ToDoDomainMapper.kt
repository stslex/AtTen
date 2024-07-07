package com.stslex.atten.feature.home.domain.model

import com.stslex.atten.core.todo.data.model.CreateTodoDataModel
import com.stslex.atten.core.todo.data.model.ToDoDataModel

fun ToDoDataModel.toDomain() = ToDoDomainModel(
    uuid = uuid,
    title = title,
    description = description,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun ToDoDomainModel.toData() = ToDoDataModel(
    uuid = uuid,
    title = title,
    description = description,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun CreateTodoDomainModel.toData() = CreateTodoDataModel(
    title = title,
    description = description,
)