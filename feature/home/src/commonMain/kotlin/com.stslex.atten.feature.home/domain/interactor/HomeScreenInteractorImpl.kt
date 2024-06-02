package com.stslex.atten.feature.home.domain.interactor

import com.stslex.atten.core.paging.model.PagingResponse
import com.stslex.atten.core.paging.model.pagingResponseMap
import com.stslex.atten.core.todo.data.global_state.ToDoGlobalState
import com.stslex.atten.core.todo.data.repository.ToDoRepository
import com.stslex.atten.feature.home.domain.model.CreateTodoDomainModel
import com.stslex.atten.feature.home.domain.model.ToDoDomainModel
import com.stslex.atten.feature.home.domain.model.toData
import com.stslex.atten.feature.home.domain.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class HomeScreenInteractorImpl(
    private val repository: ToDoRepository,
    globalState: ToDoGlobalState
) : HomeScreenInteractor {

    override val lastUpdatedNote: Flow<ToDoDomainModel> = globalState
        .lastUpdatedNote
        .filterNotNull()
        .map { it.toDomain() }

    override suspend fun getToDoList(
        page: Int,
        pageSize: Int
    ): PagingResponse<ToDoDomainModel> = repository
        .getToDoList(
            page = page,
            pageSize = pageSize
        )
        .pagingResponseMap {
            it.toDomain()
        }

    override suspend fun createItem(
        item: CreateTodoDomainModel
    ): ToDoDomainModel? = repository
        .createItem(item.toData())
        ?.toDomain()

    override suspend fun deleteItems(ids: Set<String>) {
        repository.deleteItems(ids)
    }
}