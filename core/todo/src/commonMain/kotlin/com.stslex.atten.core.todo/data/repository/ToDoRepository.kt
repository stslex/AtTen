package com.stslex.atten.core.todo.data.repository

import com.stslex.atten.core.paging.states.PagerLoadEvents
import com.stslex.atten.core.paging.states.PagerLoadState
import com.stslex.atten.core.paging.states.PagerAction
import com.stslex.atten.core.paging.states.PagingState
import com.stslex.atten.core.todo.data.model.CreateTodoDataModel
import com.stslex.atten.core.todo.data.model.ToDoDataModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface ToDoRepository {

    val pagingState: StateFlow<PagingState<ToDoDataModel>>

    val pagingLoadState: StateFlow<PagerLoadState>

    val pagingEvents: SharedFlow<PagerLoadEvents>

    suspend fun getToDo(id: String): ToDoDataModel?

    suspend fun updateItem(item: ToDoDataModel): ToDoDataModel?

    suspend fun deleteItems(ids: Set<String>)

    suspend fun createItem(item: CreateTodoDataModel): ToDoDataModel?

    fun process(action: PagerAction)
}
