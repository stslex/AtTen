package com.stslex.atten.core.navigation.di

import androidx.navigation.NavHostController
import com.stslex.atten.core.navigation.navigator.AppNavigator
import com.stslex.atten.core.navigation.navigator.AppNavigatorImpl
import org.koin.core.annotation.Module
import org.koin.dsl.module

@Module
class ModuleCoreNavigation {

    fun create(navHostController: NavHostController) = module {
        single<AppNavigator> { AppNavigatorImpl(navHostController) }
    }
}