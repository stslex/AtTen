package com.stslex.atten.core.database.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.stslex.atten.core.database.model.ToDoEntity

@Dao
interface ToDoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ToDoEntity)

    @Query("SELECT * FROM ToDoEntity WHERE uuid = :id")
    suspend fun getItem(id: String): ToDoEntity?

    @Query("SELECT * FROM ToDoEntity ORDER BY number LIMIT :pageSize OFFSET :page")
    suspend fun getItems(
        page: Int,
        pageSize: Int
    ): List<ToDoEntity>

    @Query("SELECT COUNT(*) FROM ToDoEntity")
    suspend fun getItemsCount(): Int

    @Query("UPDATE ToDoEntity SET number = number - :number WHERE number > :number")
    suspend fun decrementSequenceNumbers(number: Int)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateItem(item: ToDoEntity)

    @Query("SELECT * FROM ToDoEntity ORDER BY number DESC LIMIT 1")
    suspend fun getLastItem(): ToDoEntity?

    @Query("SELECT * FROM ToDoEntity WHERE uuid IN (:ids)")
    suspend fun getEntitiesByIds(ids: List<String>): List<ToDoEntity>

    @Query("DELETE FROM ToDoEntity WHERE uuid IN (:ids)")
    suspend fun deleteByIds(ids: List<String>)

    @Transaction
    suspend fun deleteAndReorder(ids: List<String>) {
        val entitiesToDelete = getEntitiesByIds(ids)
        deleteByIds(ids)
        entitiesToDelete.forEach { entity ->
            decrementSequenceNumbers(entity.number)
        }
    }
}