package com.stslex.atten.core.database.di

import com.stslex.atten.core.database.db.AppDatabase
import com.stslex.atten.core.database.db.ToDoDao
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.core.scope.Scope

@Module
@ComponentScan("com.stslex.atten.core.database")
class ModuleCoreDatabase {

    @Single
    fun appDatabase(scope: Scope): AppDatabase = scope.getDatabase()

    @Single
    fun todoDao(scope: Scope): ToDoDao = scope.get<AppDatabase>().getTodoDao()
}

internal expect fun Scope.getDatabase(): AppDatabase