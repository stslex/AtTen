package com.stslex.atten.feature.home.ui.use_case

import androidx.paging.PagingConfig
import androidx.paging.map
import app.cash.paging.Pager
import app.cash.paging.PagingData
import com.stslex.atten.core.todo.data.ToDoPagingSource
import com.stslex.atten.feature.home.domain.model.ToDoDomainModel
import com.stslex.atten.feature.home.domain.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoItemsPagingUseCaseImpl(
    private val toDoPagingSource: ToDoPagingSource,
) : TodoItemsPagingUseCase {

    override fun getItems(): Flow<PagingData<ToDoDomainModel>> = Pager(
        config = PagingConfig(pageSize = 15),
        pagingSourceFactory = { toDoPagingSource }
    )
        .flow
        .map { pagingData ->
            pagingData.map { it.toDomain() }
        }
}

interface TodoItemsPagingUseCase {

    fun getItems(): Flow<PagingData<ToDoDomainModel>>
}