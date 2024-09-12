package com.stslex.atten.di

import androidx.navigation.NavHostController
import com.stslex.atten.core.database.di.ModuleCoreDatabase
import com.stslex.atten.core.di.ModuleCore
import com.stslex.atten.core.navigation.di.ModuleCoreNavigation
import com.stslex.atten.core.paging.di.ModuleCorePaging
import com.stslex.atten.core.todo.di.ModuleCoreToDo
import com.stslex.atten.feature.details.di.FeatureDetailsModule
import com.stslex.atten.feature.home.di.ModuleFeatureHome
import org.koin.core.module.Module

fun appModules(
    navController: NavHostController
): List<Module> = listOf(
    ModuleCore(),
    ModuleCoreDatabase(),
    ModuleCoreToDo(),
    ModuleCorePaging(),
    ModuleFeatureHome(),
    FeatureDetailsModule(),
    ModuleCoreNavigation(navController)
).map { it.module }