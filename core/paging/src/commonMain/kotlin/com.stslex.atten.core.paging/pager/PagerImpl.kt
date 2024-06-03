package com.stslex.atten.core.paging.pager

import com.stslex.atten.core.Logger
import com.stslex.atten.core.coroutine.scope.AppCoroutineScope
import com.stslex.atten.core.paging.holder.ItemHolder
import com.stslex.atten.core.paging.model.PagingConfig
import com.stslex.atten.core.paging.model.PagingCoreData
import com.stslex.atten.core.paging.model.PagingItem
import com.stslex.atten.core.paging.model.PagingResponse
import com.stslex.atten.core.paging.states.PagerAction
import com.stslex.atten.core.paging.states.PagerLoadEvents
import com.stslex.atten.core.paging.states.PagerLoadState
import com.stslex.atten.core.paging.states.PagingState
import com.stslex.atten.core.paging.states.pagingMap
import com.stslex.atten.core.paging.worker.PagingRequestType
import com.stslex.atten.core.paging.worker.PagingWorker
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PagerImpl<T : PagingItem>(
    private val pagingConfig: PagingConfig,
    private val pagingWorker: PagingWorker,
    private val request: suspend (page: Int, pageSize: Int) -> PagingResponse<T>,
    scope: AppCoroutineScope,
    private val holder: ItemHolder<T>,
) : Pager<T> {

    private val _state = MutableStateFlow<PagingState<T>>(PagingState.default(pagingConfig))
    override val state: StateFlow<PagingState<T>> = _state.asStateFlow()

    private val _loadState = MutableStateFlow<PagerLoadState>(PagerLoadState.Initial)
    override val loadState: StateFlow<PagerLoadState> = _loadState.asStateFlow()

    private val _loadEvents = MutableSharedFlow<PagerLoadEvents>()
    override val loadEvents: SharedFlow<PagerLoadEvents> = _loadEvents.asSharedFlow()

    private var loadJob: Job? = null

    init {
        scope.launch(holder.items) { items ->
            _state.update { currentState ->
                val expectedSize = currentState.page * currentState.pageSize
                val actualSize = items.size
                val currentPage = when {
                    expectedSize > actualSize -> currentState.page.dec()
                    expectedSize < actualSize -> currentState.page.inc()
                    else -> currentState.page
                }
                // todo need research for corner cases
                currentState.copy(
                    result = items.toImmutableList(),
//                    page = currentPage,
                )
            }
        }
    }

    override fun process(action: PagerAction) {
        Logger.d("process action: $action", TAG)
        when (action) {
            is PagerAction.Initial -> processInitial()
            is PagerAction.Load -> processLoad(action.isForce)
            is PagerAction.Refresh -> processRefresh(action.isForce)
            is PagerAction.Retry -> processRetry()
        }
    }

    private fun processInitial() {
        _state.update { currentState ->
            currentState.copy(
                hasMore = true
            )
        }
        if (state.value.result.isEmpty()) {
            requestItems(isForce = true)
        }
    }

    private fun processLoad(isForce: Boolean) {
        if (
            isForce.not() &&
            (state.value.hasMore.not() || loadState.value !is PagerLoadState.Data)
        ) {
            return
        }
        _loadState.value = PagerLoadState.Loading
        requestItems(isForce = isForce)
    }

    private fun processRefresh(isForce: Boolean) {
        _loadState.value = PagerLoadState.Refresh
        _state.update { currentState ->
            currentState.copy(
                page = PagingCoreData.DEFAULT_PAGE,
            )
        }
        requestItems(isForce = isForce)
    }

    private fun processRetry() {
        if (
            loadState.value !is PagerLoadState.Error ||
            loadState.value is PagerLoadState.Initial
        ) {
            return
        }
        _loadState.value = PagerLoadState.Retry
        requestItems(isForce = false)
    }

    private fun requestItems(
        isForce: Boolean,
        requestType: PagingRequestType = PagingRequestType.DEFAULT
    ) {
        if (loadJob?.isActive == true && isForce.not()) {
            return
        }
        pagingWorker.launch(
            requestType = requestType,
            action = {
                request(
                    state.value.page,
                    state.value.pageSize
                )
            },
            onSuccess = { result ->
                val newPagingState = result.pagingMap()
                if (
                    newPagingState.result.isEmpty() &&
                    (state.value.page == PagingCoreData.DEFAULT_PAGE || state.value.result.isEmpty())
                ) {
                    holder.set(newPagingState.result)
                    _state.value = newPagingState
                    _loadState.value = PagerLoadState.Empty
                    return@launch
                }

                val newItems = if (state.value.page == PagingCoreData.DEFAULT_PAGE) {
                    newPagingState.result
                } else {
                    val oldItems = state.value.result
                    (oldItems + newPagingState.result).toImmutableList()
                }

                val resultPage = if (newItems.size < result.page * result.pageSize) {
                    result.page
                } else {
                    result.page.inc()
                }

                holder.set(newItems)

                _state.value = newPagingState.copy(
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

    companion object {
        private const val TAG = "Pager"
    }
}