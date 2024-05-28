package com.stslex.atten.core.todo.data.repository

import com.stslex.atten.core.coroutine.asyncMap
import com.stslex.atten.core.database.db.ToDoDao
import com.stslex.atten.core.todo.data.model.ToDoDataModel
import com.stslex.atten.core.todo.data.model.toData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class ToDoRepositoryImpl(
    private val dao: ToDoDao
) : ToDoRepository {

    override suspend fun getToDo(id: Long): ToDoDataModel? = withContext(Dispatchers.IO) {
        dao.getItem(id)?.toData()
    }

    override suspend fun getToDoList(
        page: Int,
        pageSize: Int
    ): List<ToDoDataModel> = withContext(Dispatchers.IO) {
        dao.getItems(
            page = page,
            pageSize = pageSize
        ).asyncMap { it.toData() }
    }
}