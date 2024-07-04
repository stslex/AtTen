package com.stslex.atten.core.database.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stslex.atten.core.database.model.ToDoEntity

@Dao
interface ToDoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ToDoEntity)

    @Query("SELECT * FROM ToDoEntity WHERE uuid = :id")
    suspend fun getItem(id: String): ToDoEntity?

    @Query("SELECT * FROM ToDoEntity ORDER BY updated_at DESC LIMIT :pageSize OFFSET ((:page - 1) * :pageSize)")
    suspend fun getItems(
        page: Int,
        pageSize: Int
    ): List<ToDoEntity>

    @Query("DELETE FROM ToDoEntity WHERE uuid IN (:ids)")
    suspend fun removeItems(ids: List<String>)
}