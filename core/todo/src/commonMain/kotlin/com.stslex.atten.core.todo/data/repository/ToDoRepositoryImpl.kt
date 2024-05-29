package com.stslex.atten.core.todo.data.repository

import com.stslex.atten.core.Logger
import com.stslex.atten.core.coroutine.asyncMap
import com.stslex.atten.core.database.db.ToDoDao
import com.stslex.atten.core.paging.model.PagingResponse
import com.stslex.atten.core.todo.data.model.CreateTodoDataModel
import com.stslex.atten.core.todo.data.model.ToDoDataModel
import com.stslex.atten.core.todo.data.model.toData
import com.stslex.atten.core.todo.data.model.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
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
    ): PagingResponse<ToDoDataModel> = withContext(Dispatchers.IO) {
        val items = async {
            dao.getItems(
                page = page,
                pageSize = pageSize
            ).asyncMap {
                it.toData()
            }
        }
        val countTotal = async {
            dao.getItemsCount()
        }
        val hasMore = async {
            items.await().size == pageSize
        }
        PagingResponse(
            page = page,
            pageSize = pageSize,
            total = countTotal.await(),
            hasMore = hasMore.await(),
            result = items.await()
        )
    }

    override suspend fun updateItem(
        item: ToDoDataModel
    ): ToDoDataModel? = withContext(Dispatchers.IO) {
        val entity = dao.getItem(item.id) ?: throw IllegalArgumentException("Item not found")
        dao.updateItem(item.toEntity(entity.number))
        dao.getItem(item.id)?.toData()
    }

    override suspend fun createItem(
        item: CreateTodoDataModel
    ): ToDoDataModel? = withContext(Dispatchers.IO) {
        val newId = dao
            .insert(item.toEntity(number = dao.getItemsCount()))
            .takeIf { it != -1L }
            ?: throw IllegalArgumentException("Item not created")
        Logger.d("New item id: $newId")
        dao.getItem(newId)?.toData()
    }

    override suspend fun deleteItem(id: Long) = withContext(Dispatchers.IO) {
        val item = dao.getItem(id) ?: throw IllegalArgumentException("Item not found")
        val itemNumber = item.number
        dao.deleteItem(id)
        dao.decreaseNumberFrom(itemNumber)
    }
}