package com.stslex.atten.feature.home.domain.interactor

import com.stslex.atten.core.paging.states.PagerLoadEvents
import com.stslex.atten.core.paging.states.PagerLoadState
import com.stslex.atten.core.paging.states.PagerAction
import com.stslex.atten.core.paging.states.PagingState
import com.stslex.atten.core.paging.states.pagingMap
import com.stslex.atten.core.todo.data.repository.ToDoRepository
import com.stslex.atten.feature.home.domain.model.CreateTodoDomainModel
import com.stslex.atten.feature.home.domain.model.ToDoDomainModel
import com.stslex.atten.feature.home.domain.model.toData
import com.stslex.atten.feature.home.domain.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

class HomeScreenInteractorImpl(
    private val repository: ToDoRepository,
) : HomeScreenInteractor {

    override val pagingState: Flow<PagingState<ToDoDomainModel>> = repository
        .pagingState
        .map { pagingState ->
            pagingState.pagingMap { it.toDomain() }
        }

    override val pagingLoadState: StateFlow<PagerLoadState> = repository.pagingLoadState
    override val pagingEvents: SharedFlow<PagerLoadEvents> = repository.pagingEvents

    override fun process(action: PagerAction) {
        repository.process(action)
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