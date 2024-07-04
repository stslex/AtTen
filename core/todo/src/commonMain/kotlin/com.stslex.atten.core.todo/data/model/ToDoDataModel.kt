package com.stslex.atten.core.todo.data.model

import com.stslex.atten.core.paging.PagingItem
import kotlinx.datetime.LocalDateTime

data class ToDoDataModel(
    val uuid: String,
    val title: String,
    val description: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) : PagingItem

data class CreateTodoDataModel(
    val title: String,
    val description: String
)