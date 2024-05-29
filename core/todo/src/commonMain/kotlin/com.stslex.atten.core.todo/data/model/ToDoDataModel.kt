package com.stslex.atten.core.todo.data.model

import com.stslex.atten.core.paging.model.PagingItem

data class ToDoDataModel(
    val id: Long,
    val title: String,
    val description: String,
    override val uniqueKey: Any
) : PagingItem

data class CreateTodoDataModel(
    val title: String,
    val description: String
)