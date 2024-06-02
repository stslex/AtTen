package com.stslex.atten.feature.details.ui.model

import androidx.compose.runtime.Stable

@Stable
data class ToDoDetailsUIModel(
    val uuid: String,
    val title: String,
    val description: String,
)
