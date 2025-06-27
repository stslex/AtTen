package com.stslex.atten.feature.details.di

import com.stslex.atten.core.core.coroutine.dispatcher.AppDispatcher
import com.stslex.atten.core.todo.data.repository.ToDoRepository
import com.stslex.atten.feature.details.domain.interactor.DetailsInteractor
import com.stslex.atten.feature.details.domain.interactor.DetailsInteractorImpl
import com.stslex.atten.feature.details.navigation.DetailsRouter
import com.stslex.atten.feature.details.ui.store.DetailsStore
import com.stslex.atten.feature.details.ui.store.DetailsStoreComponent
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module
@ComponentScan("com.stslex.atten.feature.details")
class FeatureDetailsModule {

    @Factory
    fun interactor(
        repository: ToDoRepository
    ): DetailsInteractor = DetailsInteractorImpl(repository)

    @KoinViewModel
    fun store(
        interactor: DetailsInteractor,
        appDispatcher: AppDispatcher,
        router: DetailsRouter,
    ): DetailsStore = DetailsStore(interactor, appDispatcher, router)

    @Factory
    fun router(): DetailsRouter = object : DetailsRouter {
        override fun invoke(event: DetailsStoreComponent.Navigation) {
            TODO("Not yet implemented")
        }
    }
}
