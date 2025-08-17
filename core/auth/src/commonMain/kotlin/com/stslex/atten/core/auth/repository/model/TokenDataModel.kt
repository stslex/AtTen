package com.stslex.atten.core.auth.repository.model

data class TokenDataModel(
    val uuid: String,
    val email: String,
    val accessToken: String,
    val refreshToken: String
)