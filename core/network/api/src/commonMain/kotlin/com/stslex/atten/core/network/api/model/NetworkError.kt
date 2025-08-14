package com.stslex.atten.core.network.api.model

import com.stslex.atten.core.core.model.AppError

sealed class NetworkError(
    message: String,
    cause: Throwable? = null
) : AppError(
    message = message,
    cause = cause,
) {

    data class ErrorRefresh(
        override val cause: Throwable,
    ) : NetworkError("success token not valid", cause)
}