package com.stslex.atten.feature.home.ui.store

import androidx.compose.runtime.Stable

@Stable
data class TodoUiModel(
    val id: Long,
    val title: String,
    val description: String,
)
