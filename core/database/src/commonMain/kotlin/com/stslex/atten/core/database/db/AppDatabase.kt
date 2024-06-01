package com.stslex.atten.core.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.stslex.atten.core.database.model.ToDoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(
    entities = [ToDoEntity::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getTodoDao(): ToDoDao

    companion object {
        const val DATABASE_NAME = "app_database.db"
    }
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>
): AppDatabase {
    return builder
        //.addMigrations(MIGRATIONS)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
