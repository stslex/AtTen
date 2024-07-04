package com.stslex.atten.core.todo.data.repository

import com.stslex.atten.core.todo.data.model.CreateTodoDataModel
import com.stslex.atten.core.todo.data.model.ToDoDataModel

interface ToDoRepository {

    suspend fun getItems(page: Int, pageSize: Int): List<ToDoDataModel>

    suspend fun getItem(id: String): ToDoDataModel?

    suspend fun removeItems(ids: Set<String>)

    suspend fun createItem(item: CreateTodoDataModel): ToDoDataModel?

    suspend fun updateItem(item: ToDoDataModel): ToDoDataModel?
}
