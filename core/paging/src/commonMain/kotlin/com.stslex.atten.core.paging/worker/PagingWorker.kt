package com.stslex.atten.core.paging.worker

import com.stslex.atten.core.paging.model.PagingItem
import com.stslex.atten.core.paging.model.PagingResponse
import kotlinx.coroutines.CoroutineScope

interface PagingWorker {

    fun <T : PagingItem> launch(
        requestType: PagingRequestType = PagingRequestType.DEFAULT,
        isForceLoad: Boolean = false,
        onError: suspend (Throwable) -> Unit = {},
        onSuccess: suspend CoroutineScope.(PagingResponse<T>) -> Unit = {},
        action: suspend CoroutineScope.() -> PagingResponse<T>,
    )

    fun cancel()
}
