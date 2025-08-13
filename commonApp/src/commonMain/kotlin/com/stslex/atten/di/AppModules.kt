package com.stslex.atten.di

import com.stslex.atten.core.auth.di.ModuleCoreAuth
import com.stslex.atten.core.core.di.ModuleCore
import com.stslex.atten.core.database.di.ModuleCoreDatabase
import com.stslex.atten.core.network.client.di.ModuleCoreNetwork
import com.stslex.atten.core.paging.di.ModuleCorePaging
import com.stslex.atten.core.todo.di.ModuleCoreToDo
import com.stslex.atten.core.ui.kit.utils.ModuleCoreUiUtils
import com.stslex.atten.feature.details.di.ModuleFeatureDetails
import com.stslex.atten.feature.home.di.ModuleFeatureHome
import com.stslex.atten.feature.settings.di.ModuleFeatureSettings
import org.koin.core.module.Module
import org.koin.ksp.generated.module

val appModules: List<Module> = listOf(
    ModuleCore().module,
    ModuleCoreDatabase().module,
    ModuleCoreToDo().module,
    ModuleCorePaging().module,
    ModuleCoreUiUtils().module,
    ModuleCoreAuth().module,
    ModuleCoreNetwork().module,
    ModuleFeatureHome().module,
    ModuleFeatureDetails().module,
    ModuleFeatureSettings().module
)
