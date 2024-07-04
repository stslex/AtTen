package com.stslex.atten.feature.details.domain.interactor

import com.stslex.atten.core.todo.data.repository.ToDoRepository
import com.stslex.atten.feature.details.domain.model.ToDoDetailsDomainModel
import com.stslex.atten.feature.details.domain.model.toDataModel
import com.stslex.atten.feature.details.domain.model.toDomain

class DetailsInteractorImpl(
    private val repository: ToDoRepository
) : DetailsInteractor {

    override suspend fun getItem(id: String): ToDoDetailsDomainModel? =
        repository.getItem(id)?.toDomain()

    override suspend fun updateItem(item: ToDoDetailsDomainModel): ToDoDetailsDomainModel =
        repository
            .getItem(item.uuid)
            ?.let(item::toDataModel)
            ?.let {
                repository.updateItem(it)
            }
            ?.toDomain()
            ?: throw IllegalStateException("Item not found")

    override suspend fun removeItem(id: String) {
        repository.removeItems(setOf(id))
    }
}