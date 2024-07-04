package com.stslex.atten.core.paging.pager

import com.stslex.atten.core.Logger
import com.stslex.atten.core.coroutine.scope.AppCoroutineScope
import com.stslex.atten.core.paging.holder.ItemHolder
import com.stslex.atten.core.paging.holder.ItemLoaderEvent
import com.stslex.atten.core.paging.model.PagingConfig
import com.stslex.atten.core.paging.model.PagingCoreData
import com.stslex.atten.core.paging.model.PagingItem
import com.stslex.atten.core.paging.model.PagingResponse
import com.stslex.atten.core.paging.states.PagerAction
import com.stslex.atten.core.paging.states.PagerLoadEvents
import com.stslex.atten.core.paging.states.PagerLoadState
import com.stslex.atten.core.paging.states.PagingState
import com.stslex.atten.core.paging.worker.PagingRequestType
import com.stslex.atten.core.paging.worker.PagingWorker
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PagerImpl<T : PagingItem>(
    private val pagingWorker: PagingWorker,
    private val request: suspend (page: Int, pageSize: Int) -> PagingResponse<T>,
    scope: AppCoroutineScope,
    pagingConfig: PagingConfig,
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
        scope.launch(holder.event) { event ->
            Logger.d("holder event: $event", TAG)
            when (event) {
                is ItemLoaderEvent.Create -> onItemCreated(event)
                is ItemLoaderEvent.Insert -> onItemInserted(event)
                is ItemLoaderEvent.Remove -> onItemRemoved(event)
                is ItemLoaderEvent.Update -> onItemUpdated(event)
                is ItemLoaderEvent.Replace -> onItemsReplaced(event)
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
                _state.update { currentState ->
                    currentState.copy(
                        hasMore = result.hasMore,
                        total = result.total,
                        page = result.page,
                        pageSize = result.pageSize,
                    )
                }

                if (state.value.page == PagingCoreData.DEFAULT_PAGE) {
                    holder.replace(result.result)
                    _loadState.value = PagerLoadState.Data
                } else {
                    holder.insert(result.result)
                }

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

    private fun onItemInserted(event: ItemLoaderEvent.Insert<T>) {
        _state.update { currentState ->
            val resultPage = if (
                holder.items.value.size < currentState.page * currentState.pageSize
            ) {
                currentState.page
            } else {
                currentState.page.inc()
            }
            currentState.copy(
                result = currentState.result + event.items,
                page = resultPage,
                hasMore = currentState.hasMore && event.items.isNotEmpty(),
            )
        }
    }

    private fun onItemRemoved(event: ItemLoaderEvent.Remove<T>) {
        _state.update { currentState ->
            val resultPage = if (
                holder.items.value.size < currentState.page * currentState.pageSize
            ) {
                currentState.page
            } else {
                currentState.page.inc()
            }
            currentState.copy(
                page = resultPage,
                result = currentState.result.filterNot { item ->
                    event.uuid.contains(item.uuid)
                },
            )
        }
    }

    private fun onItemUpdated(event: ItemLoaderEvent.Update<T>) {
        _state.update {
            it.copy(
                result = it.result.map { item ->
                    if (item.uuid == event.item.uuid) event.item else item
                }
            )
        }
    }

    private fun onItemsReplaced(event: ItemLoaderEvent.Replace<T>) {
        _state.update { currentState ->
            val resultPage = if (
                holder.items.value.size < currentState.page * currentState.pageSize
            ) {
                currentState.page
            } else {
                currentState.page.inc()
            }
            currentState.copy(
                result = event.items,
                page = resultPage,
                hasMore = currentState.hasMore,
            )
        }
    }

    private fun onItemCreated(event: ItemLoaderEvent.Create<T>) {
        _state.update { currentState ->
            val resultPage = if (
                holder.items.value.size < currentState.page * currentState.pageSize
            ) {
                currentState.page
            } else {
                currentState.page.inc()
            }
            currentState.copy(
                result = listOf(event.item) + currentState.result,
                page = resultPage,
                hasMore = currentState.hasMore,
            )
        }
        _loadEvents.tryEmit(PagerLoadEvents.TopInserted) // todo replace with optional for top insert
    }

    companion object {
        private const val TAG = "Pager"
    }
}