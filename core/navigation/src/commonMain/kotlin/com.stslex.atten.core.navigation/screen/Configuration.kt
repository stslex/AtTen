package com.stslex.atten.core.navigation.screen

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface Configuration {

    @Serializable
    data object HomeScreen : Configuration

    @Serializable
    data class DetailScreen(
        @SerialName("id") val id: String
    ) : Configuration
}