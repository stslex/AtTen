package com.stslex.atten.core.common

import platform.Foundation.NSUUID
import kotlin.experimental.ExperimentalNativeApi

actual val randomUuid: String
    get() = NSUUID().UUIDString()

@OptIn(ExperimentalNativeApi::class)
actual val isDebug: Boolean
    get() = Platform.isDebugBinary