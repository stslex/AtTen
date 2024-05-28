package com.stslex.atten.feature.home.domain.model

import com.stslex.atten.core.todo.data.model.ToDoDataModel

fun ToDoDataModel.toDomain() = ToDoDomainModel(
    id = id,
    title = title,
    description = description
)