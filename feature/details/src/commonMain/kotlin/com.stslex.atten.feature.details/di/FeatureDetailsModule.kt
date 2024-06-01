package com.stslex.atten.feature.details.di

import com.stslex.atten.core.di.AppModule
import com.stslex.atten.core.ui.mvi.storeOf
import com.stslex.atten.feature.details.domain.interactor.DetailsInteractor
import com.stslex.atten.feature.details.domain.interactor.DetailsInteractorImpl
import com.stslex.atten.feature.details.navigation.DetailsRouter
import com.stslex.atten.feature.details.navigation.DetailsRouterImpl
import com.stslex.atten.feature.details.ui.store.DetailsStore
import org.koin.core.annotation.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

@Module
class FeatureDetailsModule : AppModule {

    override val module = module {
        factoryOf(::DetailsInteractorImpl) { bind<DetailsInteractor>() }
        factoryOf(::DetailsRouterImpl) { bind<DetailsRouter>() }
        storeOf(::DetailsStore)
    }
}