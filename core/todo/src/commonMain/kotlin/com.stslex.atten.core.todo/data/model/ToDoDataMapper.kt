package com.stslex.atten.core.todo.data.model

import com.stslex.atten.core.database.model.ToDoEntity

fun ToDoEntity.toData() = ToDoDataModel(
    uuid = uuid,
    title = title,
    description = description,
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