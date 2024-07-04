package com.stslex.atten.feature.home.domain.model

import com.stslex.atten.core.todo.data.model.CreateTodoDataModel
import com.stslex.atten.core.todo.data.model.ToDoDataModel

fun ToDoDataModel.toDomain() = ToDoDomainModel(
    uuid = uuid,
    title = title,
    description = description,
)

fun CreateTodoDomainModel.toData() = CreateTodoDataModel(
    title = title,
    description = description,
)