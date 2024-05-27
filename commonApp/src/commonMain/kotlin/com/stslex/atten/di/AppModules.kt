package com.stslex.atten.di

import com.stslex.atten.core.di.AppModule
import com.stslex.atten.core.di.ModuleCore
import org.koin.core.module.Module

val appModules: List<Module> = listOf<AppModule>(
    ModuleCore(),
//    ModuleCoreDatabase()
)
    .map {
        it.module
    }