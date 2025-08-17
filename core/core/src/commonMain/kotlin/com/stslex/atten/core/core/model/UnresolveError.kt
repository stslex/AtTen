package com.stslex.atten.core.core.model

data class UnresolveError(
    override val message: String? = null,
    override val cause: Throwable? = null
) : AppError(
    message = message,
    cause = cause
)