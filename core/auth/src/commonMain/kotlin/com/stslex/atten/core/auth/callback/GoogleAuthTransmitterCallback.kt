package com.stslex.atten.core.auth.callback

import com.stslex.atten.core.auth.model.GoogleAuthResult

internal interface GoogleAuthTransmitterCallback {

    fun process(result: Result<GoogleAuthResult>)
}