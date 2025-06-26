package com.stslex.atten.core.database.di

import com.stslex.atten.core.database.db.AppDatabase
import com.stslex.atten.core.database.db.ToDoDao
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.scope.Scope
import org.koin.dsl.module

@Module
@ComponentScan("com.stslex.atten.core.database")
class ModuleCoreDatabase {

    val module = module {
        single<AppDatabase> { getDatabase() }
        single<ToDoDao> { get<AppDatabase>().getTodoDao() }
    }
}

internal expect fun Scope.getDatabase(): AppDatabase