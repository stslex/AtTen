package com.stslex.atten.core.paging.pager

import com.stslex.atten.core.Logger
import com.stslex.atten.core.paging.model.PagingConfig
import com.stslex.atten.core.paging.model.PagingCoreData.Companion.DEFAULT_PAGE
import com.stslex.atten.core.paging.model.PagingItem
import com.stslex.atten.core.paging.model.PagingResponse
import com.stslex.atten.core.paging.model.PagingState
import com.stslex.atten.core.paging.model.PagingUiItem
import com.stslex.atten.core.paging.model.pagingMap
import com.stslex.atten.core.paging.ui.PagingMapper
import com.stslex.atten.core.paging.worker.PagingRequestType
import com.stslex.atten.core.paging.worker.PagingWorker
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class StorePagerImpl<T : PagingUiItem, in R : PagingItem>(
    private val pagingWorker: PagingWorker,
    private val request: suspend (page: Int, pageSize: Int) -> PagingResponse<R>,
    private val mapper: PagingMapper<R, T>,
    pagingConfig: PagingConfig,
) : StorePager<T> {

    private val _state = MutableStateFlow<PagingState<T>>(PagingState.default(pagingConfig))
    override val state = _state.asStateFlow()

    private val _loadState = MutableStateFlow<PagerLoadState>(PagerLoadState.Initial)
    override val loadState = _loadState.asStateFlow()

    private val _loadEvents = MutableSharedFlow<PagerLoadEvents>()
    override val loadEvents = _loadEvents.asSharedFlow()

    private var loadJob: Job? = null

    override fun initialLoad() {
        _state.update { currentState ->
            currentState.copy(
                hasMore = true
            )
        }
        if (state.value.result.isEmpty()) {
            requestItems(isForceLoad = true)
        }
    }

    override fun load(isForceLoad: Boolean) {
        if ((state.value.hasMore.not() || loadState.value !is PagerLoadState.Data) && isForceLoad.not()) {
            return
        }
        _loadState.value = PagerLoadState.Loading
        requestItems(isForceLoad = isForceLoad)
    }

    override fun refresh(isForceLoad: Boolean) {
        _loadState.value = PagerLoadState.Refresh
        _state.update { currentState ->
            currentState.copy(
                page = DEFAULT_PAGE,
            )
        }
        requestItems(isForceLoad = isForceLoad)
    }

    override fun retry() {
        if (
            loadState.value !is PagerLoadState.Error ||
            loadState.value is PagerLoadState.Initial
        ) {
            return
        }
        _loadState.value = PagerLoadState.Retry
        requestItems(isForceLoad = false)
    }

    override fun itemInserted(item: T) {
        Logger.d("itemInserted: $item")
        _state.update { currentState ->
            val items = currentState.result
            val newItems = items + item

            val currentPage = if (newItems.size >= currentState.page * currentState.pageSize) {
                currentState.page.inc()
            } else {
                currentState.page
            }
            currentState.copy(
                result = newItems.toImmutableList(),
                page = currentPage,
            )
        }
    }

    override fun itemRemoved(uuid: Set<String>) {
        Logger.d("itemsRemoved: $uuid")
        _state.update { currentState ->
            val newItems = currentState.result
                .filter { uuid.contains(it.uuid).not() }
                .toImmutableList()
            val currentPage = if (newItems.size < currentState.page * currentState.pageSize) {
                currentState.page.dec()
            } else {
                currentState.page
            }
            currentState.copy(
                result = newItems,
                page = currentPage,
            )
        }
    }

    override fun itemUpdate(item: T) {
        Logger.d("itemUpdate: $item")
        _state.update { currentState ->
            val newItems = currentState.result
                .map { if (it.uuid == item.uuid) item else it }
                .toImmutableList()
            currentState.copy(
                result = newItems
            )
        }
    }

    private fun requestItems(
        isForceLoad: Boolean,
        requestType: PagingRequestType = PagingRequestType.DEFAULT
    ) {
        if (loadJob?.isActive == true && isForceLoad.not()) {
            return
        }
        pagingWorker.launch(
            requestType = requestType,
            action = {
                val page = state.value.page
                val pageSize = state.value.pageSize
                request(page, pageSize)
            },
            onSuccess = { result ->
                val newPagingState = result.pagingMap(mapper::invoke)
                if (
                    newPagingState.result.isEmpty() &&
                    (state.value.page == DEFAULT_PAGE || state.value.result.isEmpty())
                ) {
                    _state.value = newPagingState
                    _loadState.value = PagerLoadState.Empty
                    return@launch
                }

                val newItems = if (state.value.page == DEFAULT_PAGE) {
                    newPagingState.result
                } else {
                    val oldItems = state.value.result
                        .filter { item ->
                            newPagingState.result.none { it.uuid == item.uuid }
                        }
                        .take(
                            newPagingState.pageSize * newPagingState.page
                        )
                    (oldItems + newPagingState.result).toImmutableList()
                }

                val resultPage = if (newItems.size < result.page * result.pageSize) {
                    result.page
                } else {
                    result.page.inc()
                }

                _state.value = newPagingState.copy(
                    result = newItems,
                    page = resultPage
                )
                _loadState.value = PagerLoadState.Data
            },
            onError = { error ->
                if (
                    loadState.value is PagerLoadState.Data ||
                    loadState.value is PagerLoadState.Loading ||
                    loadState.value is PagerLoadState.Refresh
                ) {
                    _loadEvents.emit(PagerLoadEvents.Error(error))
                } else {
                    _loadState.value = PagerLoadState.Error(error)
                }
            }
        )
    }
}