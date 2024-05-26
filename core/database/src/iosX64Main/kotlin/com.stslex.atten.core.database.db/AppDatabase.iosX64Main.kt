package com.stslex.atten.core.database.db

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import platform.Foundation.NSHomeDirectory

internal fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFilePath = NSHomeDirectory() + "/${AppDatabase.DATABASE_NAME}"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFilePath,
        factory = {
            AppDatabase::class.instantiateImpl()
        }
    ).setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
}
