package com.stslex.atten.core.network.api.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthGithubRequest(
    @SerialName("code")
    val code: String,
)