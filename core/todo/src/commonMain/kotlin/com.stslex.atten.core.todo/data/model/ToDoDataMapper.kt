package com.stslex.atten.core.todo.data.model

import com.stslex.atten.core.database.model.ToDoEntity

fun ToDoEntity.toData() = ToDoDataModel(
    id = id,
    title = title,
    description = description,
    uniqueKey = id
)