package com.stslex.atten.core.network.api.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class GithubAuthResponseModel(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("scope")
    val scope: String,
    @SerialName("token_type")
    val tokenType: String
)