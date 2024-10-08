package com.stslex.atten.core.navigation.di

import androidx.navigation.NavHostController
import com.stslex.atten.core.di.AppModule
import com.stslex.atten.core.navigation.navigator.Navigator
import com.stslex.atten.core.navigation.navigator.NavigatorImpl
import org.koin.core.annotation.Module
import org.koin.dsl.module
import org.koin.core.module.Module as KoinModule

@Module
class ModuleCoreNavigation(navController: NavHostController) : AppModule {

    override val module: KoinModule = module {
        single<Navigator> { NavigatorImpl(navController) }
    }
}