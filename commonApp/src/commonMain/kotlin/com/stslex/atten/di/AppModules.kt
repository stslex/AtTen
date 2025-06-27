package com.stslex.atten.di

import com.stslex.atten.core.core.di.ModuleCore
import com.stslex.atten.core.database.di.ModuleCoreDatabase
import com.stslex.atten.core.paging.di.ModuleCorePaging
import com.stslex.atten.core.todo.di.ModuleCoreToDo
import com.stslex.atten.feature.details.di.FeatureDetailsModule
import com.stslex.atten.feature.home.di.ModuleFeatureHome
import org.koin.core.module.Module
import org.koin.ksp.generated.module

val appModules: List<Module> = listOf(
    ModuleCore().module,
    ModuleCoreDatabase().module,
    ModuleCoreToDo().module,
    ModuleCorePaging().module,
    ModuleFeatureHome().module,
    FeatureDetailsModule().module,
)
