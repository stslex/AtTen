package com.stslex.atten.core.todo.data

import com.stslex.atten.core.paging.BasePagingSource
import com.stslex.atten.core.paging.PagingResponse
import com.stslex.atten.core.todo.data.model.ToDoDataModel
import com.stslex.atten.core.todo.data.repository.ToDoRepository

class ToDoPagingSource(
    private val repository: ToDoRepository
) : BasePagingSource<ToDoDataModel>() {

    override suspend fun loadData(page: Int, pageSize: Int): PagingResponse<ToDoDataModel> {
        return PagingResponse(repository.getItems(page, pageSize))
    }
}