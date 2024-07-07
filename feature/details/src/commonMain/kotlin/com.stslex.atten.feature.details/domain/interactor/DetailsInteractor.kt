package com.stslex.atten.feature.details.domain.interactor

import com.stslex.atten.feature.details.domain.model.ToDoDetailsDomainModel
import com.stslex.atten.feature.details.domain.model.ToDoDomainUpdateModel

interface DetailsInteractor {

    suspend fun getItem(id: String): ToDoDetailsDomainModel?

    suspend fun updateItem(item: ToDoDomainUpdateModel)

    suspend fun removeItem(id: String)
}

