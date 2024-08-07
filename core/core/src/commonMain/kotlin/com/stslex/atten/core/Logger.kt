package com.stslex.atten.core

import com.stslex.atten.core.common.isDebug
import co.touchlab.kermit.Logger as Log

object Logger {

    private const val DEFAULT_TAG = "WIZARD"

    fun e(
        throwable: Throwable,
        tag: String? = null,
        message: String? = null
    ) {
        if (isDebug.not()) return
        // todo firebase crashlytics
        Log.e(
            tag = tag ?: DEFAULT_TAG,
            throwable = throwable,
            messageString = message ?: throwable.message.orEmpty(),
        )
    }

    fun d(
        message: String,
        tag: String? = null,
    ) {
        if (isDebug.not()) return
        // todo firebase analytics
        Log.d(
            tag = tag ?: DEFAULT_TAG,
            messageString = message
        )
    }
}