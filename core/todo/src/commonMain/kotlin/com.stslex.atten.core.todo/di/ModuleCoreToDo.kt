package com.stslex.atten.core.todo.di

import com.stslex.atten.core.di.AppModule
import com.stslex.atten.core.todo.data.ToDoPagingSource
import com.stslex.atten.core.todo.data.repository.ToDoRepository
import com.stslex.atten.core.todo.data.repository.ToDoRepositoryImpl
import org.koin.core.annotation.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

@Module
class ModuleCoreToDo : AppModule {

    override val module = module {
        singleOf(::ToDoRepositoryImpl) { bind<ToDoRepository>() }
        factoryOf(::ToDoPagingSource)
    }
}
