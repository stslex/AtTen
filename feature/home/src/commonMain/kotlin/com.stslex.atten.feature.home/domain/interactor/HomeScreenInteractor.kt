package com.stslex.atten.feature.home.domain.interactor

import com.stslex.atten.core.paging.model.PagingResponse
import com.stslex.atten.feature.home.domain.model.CreateTodoDomainModel
import com.stslex.atten.feature.home.domain.model.ToDoDomainModel

interface HomeScreenInteractor {

    suspend fun getToDoList(
        page: Int,
        pageSize: Int
    ): PagingResponse<ToDoDomainModel>

    suspend fun deleteItems(ids: Set<String>)

    suspend fun createItem(item: CreateTodoDomainModel): ToDoDomainModel?
}
