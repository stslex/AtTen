package com.stslex.atten.feature.home.domain.interactor

import com.stslex.atten.core.paging.model.PagingResponse
import com.stslex.atten.feature.home.domain.model.CreateTodoDomainModel
import com.stslex.atten.feature.home.domain.model.ToDoDomainModel
import kotlinx.coroutines.flow.Flow

interface HomeScreenInteractor {

    val lastUpdatedNote: Flow<ToDoDomainModel>

    suspend fun getToDoList(
        page: Int,
        pageSize: Int
    ): PagingResponse<ToDoDomainModel>

    suspend fun deleteItems(ids: Set<String>)

    suspend fun createItem(item: CreateTodoDomainModel): ToDoDomainModel?
}
