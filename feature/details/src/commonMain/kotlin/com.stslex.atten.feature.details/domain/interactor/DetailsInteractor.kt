package com.stslex.atten.feature.details.domain.interactor

import com.stslex.atten.feature.details.domain.model.ToDoDetailsDomainModel

interface DetailsInteractor {

    suspend fun getItem(id: String): ToDoDetailsDomainModel?

    suspend fun updateItem(item: ToDoDetailsDomainModel): ToDoDetailsDomainModel?

    suspend fun removeItem(id: String)
}

