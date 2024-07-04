package com.stslex.atten.feature.home.domain.interactor

import com.stslex.atten.core.todo.data.repository.ToDoRepository
import com.stslex.atten.feature.home.domain.model.CreateTodoDomainModel
import com.stslex.atten.feature.home.domain.model.ToDoDomainModel
import com.stslex.atten.feature.home.domain.model.toData
import com.stslex.atten.feature.home.domain.model.toDomain

class HomeScreenInteractorImpl(
    private val repository: ToDoRepository,
) : HomeScreenInteractor {

    override suspend fun deleteItems(ids: Set<String>) {
        repository.removeItems(ids)
    }

    override suspend fun createItem(item: CreateTodoDomainModel): ToDoDomainModel? = repository
        .createItem(item.toData())
        ?.toDomain()
}