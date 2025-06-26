package com.stslex.atten.core.logger

interface Logger {

    fun e(
        throwable: Throwable,
        message: String? = null
    )

    fun d(message: String)

    fun i(message: String)

    fun v(message: String)
}

