package com.stslex.atten.feature.details.domain.interactor

import com.stslex.atten.core.todo.data.repository.ToDoRepository
import com.stslex.atten.feature.details.di.DetailsScope
import com.stslex.atten.feature.details.domain.model.ToDoDetailsDomainModel
import com.stslex.atten.feature.details.domain.model.ToDoDomainUpdateModel
import com.stslex.atten.feature.details.domain.model.toDataModel
import com.stslex.atten.feature.details.domain.model.toDomain
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Scoped

@Factory
@Scope(DetailsScope::class)
@Scoped
class DetailsInteractorImpl(
    private val repository: ToDoRepository
) : DetailsInteractor {

    override suspend fun getItem(id: String): ToDoDetailsDomainModel? =
        repository.getToDo(id)?.toDomain()

    override suspend fun updateItem(item: ToDoDomainUpdateModel) {
        repository.updateItem(item.toDataModel())
    }

    override suspend fun removeItem(id: String) {
        repository.deleteItems(setOf(id))
    }
}