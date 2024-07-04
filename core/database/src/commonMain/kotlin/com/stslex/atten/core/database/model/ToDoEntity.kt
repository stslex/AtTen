package com.stslex.atten.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stslex.atten.core.common.randomUuid
import kotlinx.serialization.SerialName

@Entity
data class ToDoEntity(
    @PrimaryKey(autoGenerate = false)
    @SerialName("uuid")
    val uuid: String = randomUuid,
    @SerialName("number")
    var number: Int,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
)
