package com.stslex.atten.core.core.model

open class AppError(
    message: String? = null,
    cause: Throwable? = null
) : Throwable(
    message = message,
    cause = cause,
)