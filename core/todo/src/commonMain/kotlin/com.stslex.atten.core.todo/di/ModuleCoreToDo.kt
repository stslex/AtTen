package com.stslex.atten.core.todo.di

import com.stslex.atten.core.di.AppModule
import com.stslex.atten.core.todo.data.global_state.ToDoGlobalState
import com.stslex.atten.core.todo.data.global_state.ToDoGlobalStateImpl
import com.stslex.atten.core.todo.data.repository.ToDoRepository
import com.stslex.atten.core.todo.data.repository.ToDoRepositoryImpl
import org.koin.core.annotation.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

@Module
class ModuleCoreToDo : AppModule {

    override val module = module {
        singleOf(::ToDoRepositoryImpl) { bind<ToDoRepository>() }
        singleOf(::ToDoGlobalStateImpl) { bind<ToDoGlobalState>() }
    }
}