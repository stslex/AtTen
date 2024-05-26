package com.stslex.atten.core.database.di

import com.stslex.atten.core.database.db.AppDatabase
import com.stslex.atten.core.database.db.getDatabaseBuilder
import com.stslex.atten.core.database.db.getRoomDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.Scope

internal actual fun Scope.getDatabase(): AppDatabase = getRoomDatabase(
    getDatabaseBuilder(
        context = androidContext()
    )
)