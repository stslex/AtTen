package com.stslex.atten.core.network.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponseModel(
    @SerialName("uuid")
    val uuid: String,
    @SerialName("email")
    val email: String,
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("refresh_token")
    val refreshToken: String
)