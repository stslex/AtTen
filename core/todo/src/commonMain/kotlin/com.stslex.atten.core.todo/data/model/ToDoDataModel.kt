package com.stslex.atten.core.todo.data.model

import com.stslex.atten.core.paging.model.PagingItem

data class ToDoDataModel(
    override val uuid: String,
    val title: String,
    val description: String,
) : PagingItem

data class CreateTodoDataModel(
    val title: String,
    val description: String
)