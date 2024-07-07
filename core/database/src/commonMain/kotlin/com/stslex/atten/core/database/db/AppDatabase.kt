package com.stslex.atten.core.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import androidx.sqlite.execSQL
import com.stslex.atten.core.database.model.ToDoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.datetime.Clock

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
                    val dateTime = Clock.System.now().toEpochMilliseconds() - i * 10
                    val entity = ToDoEntity(
                        title = "Title $i",
                        description = "Description $i",
                        createdAt = dateTime,
                        updatedAt = dateTime
                    )
                    connection.execSQL(
                        "INSERT INTO ToDoEntity (uuid, title, description, created_at, updated_at) " +
                                "VALUES ('${entity.uuid}', '${entity.title}', '${entity.description}', '${entity.createdAt}', '${entity.updatedAt}')"
                    )
                }
                super.onCreate(connection)
            }
        })
        .build()
}
