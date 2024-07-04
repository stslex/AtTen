package com.stslex.atten.core.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import androidx.sqlite.execSQL
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
        .fallbackToDestructiveMigration(true)
        //.addMigrations(MIGRATIONS)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(connection: SQLiteConnection) {
                // todo for tests
                for (i in 1..100) {
                    val entity = ToDoEntity(
                        number = i,
                        title = "Title $i",
                        description = "Description $i",
                    )
                    connection.execSQL(
                        "INSERT INTO ToDoEntity (uuid, number, title, description) " +
                                "VALUES ('${entity.uuid}', ${entity.number}, '${entity.title}', '${entity.description}')"
                    )
                }
                super.onCreate(connection)
            }
        })
        .build()
}
