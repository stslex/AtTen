package com.stslex.atten.core.navigation.di

import com.arkivanov.decompose.router.stack.StackNavigation
import com.stslex.atten.core.navigation.screen.Configuration
import com.stslex.atten.core.navigation.navigator.AppNavigator
import com.stslex.atten.core.navigation.navigator.AppNavigatorImpl
import org.koin.core.annotation.Module
import org.koin.dsl.module

@Module
class ModuleCoreNavigation {

    fun create(navigation: StackNavigation<Configuration>) = module {
        single<AppNavigator> { AppNavigatorImpl(navigation) }
    }
}