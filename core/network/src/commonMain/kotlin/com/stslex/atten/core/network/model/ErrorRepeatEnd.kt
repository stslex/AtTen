package com.stslex.atten.core.network.model

import com.stslex.atten.core.core.model.AppError


sealed class NetworkError(
    message: String,
    cause: Throwable? = null
) : AppError(
    message = message,
    cause = cause,
) {

    /**
     * Error repeat request.
     * Show that the request was repeated after a refresh token
     * @see com.stslex.atten.core.network.api.AppHttpApiImpl.request
     */
    internal data object ErrorRepeatEnd : NetworkError("")

    data class ErrorRefresh(
        override val cause: Throwable,
    ) : NetworkError("success token not valid", cause)
}