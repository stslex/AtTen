package com.stslex.atten.core.database.di

import com.stslex.atten.core.database.db.AppDatabase
import com.stslex.atten.core.database.db.ToDoDao
import com.stslex.atten.core.di.AppModule
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton
import org.koin.core.scope.Scope
import org.koin.dsl.module

@Module
@Singleton
class ModuleCoreDatabase : AppModule {

    override val module = module {
        single<AppDatabase> { getDatabase() }
        single<ToDoDao> { get<AppDatabase>().getTodoDao() }
    }
}

internal expect fun Scope.getDatabase(): AppDatabase