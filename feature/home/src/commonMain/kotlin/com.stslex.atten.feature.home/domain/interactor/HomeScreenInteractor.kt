package com.stslex.atten.feature.home.domain.interactor

import com.stslex.atten.core.paging.states.PagerLoadEvents
import com.stslex.atten.core.paging.states.PagerLoadState
import com.stslex.atten.core.paging.states.PagerAction
import com.stslex.atten.core.paging.states.PagingState
import com.stslex.atten.feature.home.domain.model.CreateTodoDomainModel
import com.stslex.atten.feature.home.domain.model.ToDoDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface HomeScreenInteractor {

    val pagingState: Flow<PagingState<ToDoDomainModel>>

    val pagingLoadState: StateFlow<PagerLoadState>

    val pagingEvents: SharedFlow<PagerLoadEvents>

    fun process(action: PagerAction)

    suspend fun deleteItems(ids: Set<String>)

    suspend fun createItem(item: CreateTodoDomainModel): ToDoDomainModel?
}
