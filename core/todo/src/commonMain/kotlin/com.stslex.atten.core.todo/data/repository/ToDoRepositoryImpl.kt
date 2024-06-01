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

    override suspend fun getToDo(id: String): ToDoDataModel? = withContext(Dispatchers.IO) {
        dao.getItem(id)?.toData()
    }

    override suspend fun getToDoList(
        page: Int,
        pageSize: Int
    ): PagingResponse<ToDoDataModel> = withContext(Dispatchers.IO) {
        val items = async {
            dao.getItems(page, pageSize)
                .asyncMap { it.toData() }
        }
        val countTotal = async {
            dao.getItemsCount()
        }
        PagingResponse(
            page = page,
            pageSize = pageSize,
            total = countTotal.await(),
            hasMore = items.await().size == pageSize,
            result = items.await()
        )
    }

    override suspend fun updateItem(
        item: ToDoDataModel
    ): ToDoDataModel? = withContext(Dispatchers.IO) {
        val entity = dao.getItem(item.uuid) ?: throw IllegalArgumentException("Item not found")
        dao.updateItem(item.toEntity(entity.number))
        dao.getItem(item.uuid)?.toData()
    }

    override suspend fun createItem(
        item: CreateTodoDataModel
    ): ToDoDataModel? = withContext(Dispatchers.IO) {
        val entity = item.toEntity(number = dao.getItemsCount())
        val newId = dao.insert(entity)
        Logger.d("New item id: $newId")
        dao.getItem(entity.uuid)?.toData()
    }

    override suspend fun deleteItems(ids: Set<String>) = withContext(Dispatchers.IO) {
        dao.deleteAndReorder(ids.toList())
    }

    override suspend fun replace(numberFrom: Int, numberTo: Int) {
        withContext(Dispatchers.IO) {
//            dao.decreaseNumberFrom(numberFrom)
//            dao.updateItem(dao.getItem(numberTo.toLong())!!.copy(number = numberFrom))
        }
    }
}