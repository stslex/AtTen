package com.stslex.atten.core.todo.data.repository

import com.stslex.atten.core.paging.model.PagingResponse
import com.stslex.atten.core.todo.data.model.ToDoDataModel

interface ToDoRepository {

    suspend fun getToDo(id: Long): ToDoDataModel?

    suspend fun getToDoList(
        page: Int,
        pageSize: Int
    ): PagingResponse<ToDoDataModel>
}

