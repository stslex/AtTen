package com.stslex.atten.core.todo.di

import com.stslex.atten.core.di.AppModule
import com.stslex.atten.core.paging.holder.ItemHolder
import com.stslex.atten.core.paging.holder.ItemHolderImpl
import com.stslex.atten.core.todo.data.model.ToDoDataModel
import com.stslex.atten.core.todo.data.repository.ToDoRepository
import com.stslex.atten.core.todo.data.repository.ToDoRepositoryImpl
import org.koin.core.annotation.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

@Module
class ModuleCoreToDo : AppModule {

    override val module = module {
        single<ItemHolder<ToDoDataModel>> { ItemHolderImpl() }
        singleOf(::ToDoRepositoryImpl) { bind<ToDoRepository>() }
    }
}
