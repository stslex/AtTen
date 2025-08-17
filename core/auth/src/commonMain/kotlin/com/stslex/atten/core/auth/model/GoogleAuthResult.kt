package com.stslex.atten.core.auth.model

sealed interface GoogleAuthResult {

    data class Success(val data: GoogleAuthData) : GoogleAuthResult

    data object Cancelled : GoogleAuthResult
}