package com.stslex.atten.core.ui.navigation

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

@Serializable
@Stable
sealed interface Config {

    val isBackAllow: Boolean
        get() = true

    data object Home : Config

    data class Detail(
        val id: String
    ) : Config
}