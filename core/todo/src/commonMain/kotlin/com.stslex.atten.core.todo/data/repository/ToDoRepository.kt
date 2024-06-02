package com.stslex.atten.core.todo.data.repository

import com.stslex.atten.core.paging.model.PagingResponse
import com.stslex.atten.core.todo.data.model.CreateTodoDataModel
import com.stslex.atten.core.todo.data.model.ToDoDataModel

interface ToDoRepository {

    suspend fun getToDo(id: String): ToDoDataModel?

    suspend fun getToDoList(
        page: Int,
        pageSize: Int
    ): PagingResponse<ToDoDataModel>

    suspend fun updateItem(item: ToDoDataModel): ToDoDataModel?

    suspend fun deleteItems(ids: Set<String>)

    suspend fun createItem(item: CreateTodoDataModel): ToDoDataModel?

    suspend fun replace(numberFrom: Int, numberTo: Int)
}
