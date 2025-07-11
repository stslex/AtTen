package com.stslex.atten.core.ui.navigation

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

@Serializable
@Stable
sealed interface Config {

    val isBackAllow: Boolean
        get() = true

    @Serializable
    data object Home : Config {

        override val isBackAllow: Boolean = false
    }

    @Serializable
    data class Detail(
        val uuid: String
    ) : Config

    @Serializable
    data object Settings : Config
}