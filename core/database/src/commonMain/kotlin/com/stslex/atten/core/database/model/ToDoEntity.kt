package com.stslex.atten.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName

@Entity
data class ToDoEntity(
    @PrimaryKey(autoGenerate = true)
    @SerialName("id")
    val id: Long = 0L,
    @SerialName("number")
    val number: Int,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
)
