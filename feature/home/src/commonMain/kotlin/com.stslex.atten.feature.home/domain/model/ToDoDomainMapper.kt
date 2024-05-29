package com.stslex.atten.feature.home.domain.model

import com.stslex.atten.core.todo.data.model.CreateTodoDataModel
import com.stslex.atten.core.todo.data.model.ToDoDataModel

fun ToDoDataModel.toDomain() = ToDoDomainModel(
    id = id,
    title = title,
    description = description,
    uniqueKey = uniqueKey.toString()
)

fun ToDoDomainModel.toData() = ToDoDataModel(
    id = id,
    title = title,
    description = description,
    uniqueKey = uniqueKey
)

fun CreateTodoDomainModel.toData() = CreateTodoDataModel(
    title = title,
    description = description,
)