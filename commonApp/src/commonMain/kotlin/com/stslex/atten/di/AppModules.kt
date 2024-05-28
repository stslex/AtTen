package com.stslex.atten.di

import com.stslex.atten.core.database.di.ModuleCoreDatabase
import com.stslex.atten.core.di.ModuleCore
import com.stslex.atten.core.paging.di.ModuleCorePaging
import com.stslex.atten.core.todo.di.ModuleCoreToDo
import com.stslex.atten.feature.home.di.ModuleFeatureHome
import org.koin.core.module.Module

val appModules: List<Module> = listOf(
    ModuleCore(),
    ModuleCoreDatabase(),
    ModuleCoreToDo(),
    ModuleCorePaging(),
    ModuleFeatureHome(),
).map { it.module }