package com.stslex.atten.core.database.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.stslex.atten.core.database.model.ToDoEntity

@Dao
interface ToDoDao {

    @Insert
    suspend fun insert(item: ToDoEntity)

    @Query("SELECT * FROM ToDoEntity WHERE id = :id")
    suspend fun getItem(id: Long): ToDoEntity?

    @Query("SELECT * FROM ToDoEntity ORDER BY number LIMIT :pageSize OFFSET :page")
    suspend fun getItems(
        page: Int,
        pageSize: Int
    ): List<ToDoEntity>

    @Query("SELECT COUNT(*) FROM ToDoEntity")
    suspend fun getItemsCount(): Int
}