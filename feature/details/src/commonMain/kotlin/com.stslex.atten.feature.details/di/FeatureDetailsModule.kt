package com.stslex.atten.feature.details.di

import com.stslex.atten.core.ui.kit.mvi.storeOf
import com.stslex.atten.feature.details.domain.interactor.DetailsInteractor
import com.stslex.atten.feature.details.domain.interactor.DetailsInteractorImpl
import com.stslex.atten.feature.details.ui.store.DetailsStore
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

@Module
@ComponentScan("com.stslex.atten.feature.details")
class FeatureDetailsModule {

    val module = module {
        factoryOf(::DetailsInteractorImpl) { bind<DetailsInteractor>() }
//        factoryOf(::DetailsRouterImpl) { bind<DetailsRouter>() }
        storeOf(::DetailsStore)
    }
}
