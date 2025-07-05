package com.stslex.atten.core.auth.callback

import com.stslex.atten.core.auth.model.GoogleAuthResult
import org.koin.core.annotation.Factory

@Factory
internal class GoogleAuthCallbackImpl : GoogleAuthCallback {

    private var callback: (Result<GoogleAuthResult>) -> Unit = {}

    override operator fun invoke(block: (Result<GoogleAuthResult>) -> Unit) {
        callback = block
    }

    override fun process(result: Result<GoogleAuthResult>) {
        callback.invoke(result)
    }
}

