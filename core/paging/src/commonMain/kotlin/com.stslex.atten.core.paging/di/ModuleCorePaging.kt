package com.stslex.atten.core.paging.di

import com.stslex.atten.core.di.AppModule
import com.stslex.atten.core.paging.factory.PagerFactory
import com.stslex.atten.core.paging.factory.PagerFactoryImpl
import org.koin.core.annotation.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

@Module
class ModuleCorePaging : AppModule {

    override val module = module {
        singleOf(::PagerFactoryImpl) { bind<PagerFactory>() }
    }
}