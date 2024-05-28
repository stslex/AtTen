package com.stslex.atten.feature.home.domain.interactor

import com.stslex.atten.core.coroutine.asyncMap
import com.stslex.atten.core.todo.data.repository.ToDoRepository
import com.stslex.atten.feature.home.domain.model.ToDoDomainModel
import com.stslex.atten.feature.home.domain.model.toDomain

class HomeScreenInteractorImpl(
    private val repository: ToDoRepository
) : HomeScreenInteractor {

    override suspend fun getToDoList(
        page: Int,
        pageSize: Int
    ): List<ToDoDomainModel> = repository
        .getToDoList(
            page = page,
            pageSize = pageSize
        )
        .asyncMap {
            it.toDomain()
        }
}