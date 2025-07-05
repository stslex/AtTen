package com.stslex.atten.core.auth.callback

import com.stslex.atten.core.auth.model.GoogleAuthResult

fun interface GoogleAuthReceiverCallback {

    operator fun invoke(block: (Result<GoogleAuthResult>) -> Unit)
}