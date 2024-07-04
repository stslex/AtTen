package com.stslex.atten.core.todo.data.repository

import com.stslex.atten.core.Logger
import com.stslex.atten.core.coroutine.asyncMap
import com.stslex.atten.core.coroutine.dispatcher.AppDispatcher
import com.stslex.atten.core.database.db.ToDoDao
import com.stslex.atten.core.todo.data.model.CreateTodoDataModel
import com.stslex.atten.core.todo.data.model.ToDoDataModel
import com.stslex.atten.core.todo.data.model.toCreateEntity
import com.stslex.atten.core.todo.data.model.toData
import com.stslex.atten.core.todo.data.model.toUpdatedEntity
import kotlinx.coroutines.withContext

class ToDoRepositoryImpl(
    private val dao: ToDoDao,
    private val appDispatcher: AppDispatcher
) : ToDoRepository {

    override suspend fun getItems(
        page: Int,
        pageSize: Int
    ): List<ToDoDataModel> = withContext(appDispatcher.io) {
        dao
            .getItems(
                page = page,
                pageSize = pageSize
            )
            .also {
                Logger.d("getItems page: $page pageSize: $pageSize totalRes: ${it.size}: $it")
            }
            .asyncMap { it.toData() }
    }

    override suspend fun getItem(id: String): ToDoDataModel? = withContext(appDispatcher.io) {
        dao.getItem(id)?.toData()
    }

    override suspend fun removeItems(ids: Set<String>) {
        withContext(appDispatcher.io) {
            dao.removeItems(ids.toList())
        }
    }

    override suspend fun createItem(
        item: CreateTodoDataModel
    ): ToDoDataModel? = withContext(appDispatcher.io) {
        val createdItem = item.toCreateEntity()
        dao.insert(createdItem)
        dao.getItem(createdItem.uuid)?.toData()
    }

    override suspend fun updateItem(
        item: ToDoDataModel
    ): ToDoDataModel? = withContext(appDispatcher.io) {
        val updatedItem = item.toUpdatedEntity()
        dao.insert(updatedItem)
        dao.getItem(updatedItem.uuid)?.toData()
    }
}