package com.stslex.atten.core.todo.data.model

import com.stslex.atten.core.database.model.ToDoEntity

fun ToDoEntity.toData() = ToDoDataModel(
    uuid = uuid,
    title = title,
    description = description,
)

fun ToDoDataModel.toUpdatedEntity(
    number: Int
) = ToDoEntity(
    uuid = uuid,
    number = number,
    title = title,
    description = description
)

fun CreateTodoDataModel.toCreateEntity(
    number: Int
) = ToDoEntity(
    number = number,
    title = title,
    description = description,
)