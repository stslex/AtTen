package com.stslex.atten.feature.home.di

import com.stslex.atten.core.di.AppModule
import com.stslex.atten.core.ui.kit.mvi.storeOf
import com.stslex.atten.feature.home.domain.interactor.HomeScreenInteractor
import com.stslex.atten.feature.home.domain.interactor.HomeScreenInteractorImpl
import com.stslex.atten.feature.home.navigation.HomeRouter
import com.stslex.atten.feature.home.navigation.HomeRouterImpl
import com.stslex.atten.feature.home.ui.store.HomeStore
import org.koin.core.annotation.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

@Module
class ModuleFeatureHome : AppModule {

    override val module = module {
        factoryOf(::HomeScreenInteractorImpl) { bind<HomeScreenInteractor>() }
        factoryOf(::HomeRouterImpl) { bind<HomeRouter>() }
        storeOf(::HomeStore)
    }
}
