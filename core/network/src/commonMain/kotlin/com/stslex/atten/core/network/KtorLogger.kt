package com.stslex.atten.core.network

import com.stslex.atten.core.core.logger.Log
import io.ktor.client.plugins.logging.Logger

internal object KtorLogger : Logger {

    private const val TAG = "KTOR_LOGGER"
    private val logger = Log.tag(TAG)

    override fun log(message: String) {
        logger.v(message)
    }
}