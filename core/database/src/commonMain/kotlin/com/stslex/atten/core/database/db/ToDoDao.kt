package com.stslex.atten.core.database.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.stslex.atten.core.database.model.ToDoEntity

@Dao
interface ToDoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ToDoEntity): Long

    @Query("SELECT * FROM ToDoEntity WHERE id = :id")
    suspend fun getItem(id: Long): ToDoEntity?

    @Query("SELECT * FROM ToDoEntity ORDER BY id LIMIT :pageSize OFFSET :page")
    suspend fun getItems(
        page: Int,
        pageSize: Int
    ): List<ToDoEntity>

    @Query("SELECT COUNT(*) FROM ToDoEntity")
    suspend fun getItemsCount(): Int

    @Query("DELETE FROM ToDoEntity WHERE id = :id")
    suspend fun deleteItem(id: Long)

    @Query("UPDATE ToDoEntity SET number = number - :number WHERE number > :number")
    suspend fun decreaseNumberFrom(number: Int)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateItem(item: ToDoEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM ToDoEntity WHERE id = :id)")
    suspend fun isItemExist(id: Long): Boolean

    @Query("SELECT * FROM ToDoEntity ORDER BY number DESC LIMIT 1")
    suspend fun getLastItem(): ToDoEntity?
}