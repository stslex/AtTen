package com.stslex.atten.core.database.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ToDoStatus {
    @SerialName("open")
    OPEN,

    @SerialName("in_progress")
    IN_PROGRESS,

    @SerialName("done")
    DONE
}