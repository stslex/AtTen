package com.stslex.atten.core.navigation.decompose

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext

@Stable
sealed class AppComponents(
    context: ComponentContext
) : ComponentContext by context {

    @Stable
    data class HomeComponent(
        private val context: ComponentContext,
    ) : AppComponents(context)

    @Stable
    data class DetailComponent(
        val id: String,
        private val context: ComponentContext,
    ) : AppComponents(context)
}