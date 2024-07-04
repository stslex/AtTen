package com.stslex.atten.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stslex.atten.core.common.randomUuid

@Entity
data class ToDoEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("uuid")
    val uuid: String = randomUuid,
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("description")
    val description: String,
    @ColumnInfo("created_at")
    val createdAt: Long,
    @ColumnInfo("updated_at")
    val updatedAt: Long,
)
