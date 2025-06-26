package com.stslex.atten.core.ui.navigation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {

    @Serializable
    data object Home : Screen

    @Serializable
    data class DetailScreen(
        @SerialName("id") val id: String
    ) : Screen
}
