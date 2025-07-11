package com.stslex.atten.core.todo.data.repository

import com.stslex.atten.core.core.coroutine.asyncMap
import com.stslex.atten.core.core.coroutine.coroutineExceptionHandler
import com.stslex.atten.core.core.coroutine.dispatcher.AppDispatcher
import com.stslex.atten.core.core.coroutine.scope.AppCoroutineScope
import com.stslex.atten.core.database.db.ToDoDao
import com.stslex.atten.core.paging.factory.PagerFactory
import com.stslex.atten.core.paging.model.PagingResponse
import com.stslex.atten.core.paging.states.PagerAction
import com.stslex.atten.core.paging.states.PagerLoadEvents
import com.stslex.atten.core.paging.states.PagerLoadState
import com.stslex.atten.core.paging.states.PagingState
import com.stslex.atten.core.todo.data.ToDoItemHolder
import com.stslex.atten.core.todo.data.model.CreateTodoDataModel
import com.stslex.atten.core.todo.data.model.ToDoDataModel
import com.stslex.atten.core.todo.data.model.UpdateTodoDataModel
import com.stslex.atten.core.todo.data.model.toCreateEntity
import com.stslex.atten.core.todo.data.model.toData
import com.stslex.atten.core.todo.data.model.toUpdatedEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

@Single
class ToDoRepositoryImpl(
    private val dao: ToDoDao,
    private val itemsHolder: ToDoItemHolder,
    pagerFactory: PagerFactory,
    private val appDispatcher: AppDispatcher
) : ToDoRepository {

    private val pager by lazy {
        pagerFactory.create(
            scope = AppCoroutineScope(
                scope = CoroutineScope(appDispatcher.io + SupervisorJob() + coroutineExceptionHandler),
                appDispatcher = appDispatcher
            ),
            holder = itemsHolder,
            request = { page, pageSize -> getToDoList(page, pageSize) }
        )
    }

    override val pagingState: StateFlow<PagingState<ToDoDataModel>> = pager.state
    override val pagingLoadState: StateFlow<PagerLoadState> = pager.loadState
    override val pagingEvents: SharedFlow<PagerLoadEvents> = pager.loadEvents

    override fun process(action: PagerAction) {
        pager.process(action)
    }

    override suspend fun getToDo(id: String): ToDoDataModel? = pagingState
        .value
        .result
        .find { it.uuid == id }
        ?: withContext(appDispatcher.io) {
            dao.getItem(id)?.toData()
        }

    private suspend fun getToDoList(
        page: Int,
        pageSize: Int
    ): PagingResponse<ToDoDataModel> = withContext(appDispatcher.io) {
        val items = async {
            dao.getItems(page, pageSize).asyncMap { it.toData() }
        }
        val countTotal = async {
            dao.getItemsCount()
        }
        PagingResponse(
            page = page,
            pageSize = pageSize,
            total = countTotal.await(),
            hasMore = countTotal.await() > page * pageSize,
            result = items.await()
        )
    }

    override suspend fun updateItem(
        item: UpdateTodoDataModel
    ): ToDoDataModel = withContext(Dispatchers.IO) {
        dao.insert(item.toUpdatedEntity())
        dao.getItem(item.uuid)
            ?.toData()
            ?.also { itemsHolder.updateAndReplace(it) }
            ?: throw IllegalArgumentException("Item not found")
    }

    override suspend fun createItem(
        item: CreateTodoDataModel
    ): ToDoDataModel? = withContext(Dispatchers.IO) {
        val entity = item.toCreateEntity()
        dao.insert(entity)
        dao.getItem(entity.uuid)
            ?.toData()
            ?.also { itemsHolder.create(it) }
    }

    override suspend fun deleteItems(ids: Set<String>) = withContext(Dispatchers.IO) {
        dao.deleteByIds(ids.toList())
        itemsHolder.remove(ids)
    }
}