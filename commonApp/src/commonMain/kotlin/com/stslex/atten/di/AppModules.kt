package com.stslex.atten.di

import com.stslex.atten.core.database.di.ModuleCoreDatabase
import com.stslex.atten.core.di.ModuleCore
import com.stslex.atten.core.todo.di.ModuleCoreToDo
import com.stslex.atten.feature.home.di.ModuleFeatureHome
import org.koin.core.module.Module

val appModules: List<Module> = listOf(
    ModuleCore(),
    ModuleCoreDatabase(),
    ModuleCoreToDo(),
    ModuleFeatureHome(),
).map { it.module }