package com.stslex.atten.core.todo.data.model

import com.stslex.atten.core.database.model.ToDoEntity

fun ToDoEntity.toData() = ToDoDataModel(
    id = id,
    title = title,
    description = description,
    uniqueKey = id
)

fun ToDoDataModel.toEntity(
    number: Int
) = ToDoEntity(
    number = number,
    title = title,
    description = description
)

fun CreateTodoDataModel.toEntity(
    number: Int
) = ToDoEntity(
    number = number,
    title = title,
    description = description,
)