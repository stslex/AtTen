package com.stslex.atten.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginOkResponse(
    @SerialName("uuid")
    val uuid: String,
    @SerialName("email")
    val email: String,
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("refresh_token")
    val refreshToken: String
)