package com.stslex.atten.core.di

import com.stslex.atten.core.coroutine.dispatcher.AppDispatcher
import com.stslex.atten.core.coroutine.dispatcher.AppDispatcherImpl
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.core.module.Module as KoinModule

@Module
@ComponentScan
class ModuleCore : AppModule {

    override val module: KoinModule = module {
        singleOf(::AppDispatcherImpl) { bind<AppDispatcher>() }
    }
}