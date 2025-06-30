package com.stslex.atten.core.core.common

import com.stslex.atten.core.core.BuildConfig
import java.util.UUID

actual val randomUuid: String
    get() = UUID.randomUUID().toString()

actual val isDebug: Boolean
    get() = BuildConfig.DEBUG