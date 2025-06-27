package com.stslex.atten.feature.home.di

import com.stslex.atten.core.core.coroutine.dispatcher.AppDispatcher
import com.stslex.atten.core.todo.data.repository.ToDoRepository
import com.stslex.atten.feature.home.domain.interactor.HomeScreenInteractor
import com.stslex.atten.feature.home.domain.interactor.HomeScreenInteractorImpl
import com.stslex.atten.feature.home.navigation.HomeRouter
import com.stslex.atten.feature.home.ui.store.HomeStore
import com.stslex.atten.feature.home.ui.store.HomeStoreComponent
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module


@Module
@ComponentScan("com.stslex.atten.feature.home")
class ModuleFeatureHome {

    @Factory
    fun interactor(
        repository: ToDoRepository
    ): HomeScreenInteractor = HomeScreenInteractorImpl(repository)

    @KoinViewModel
    fun store(
        interactor: HomeScreenInteractor,
        appDispatcher: AppDispatcher,
        router: HomeRouter,
    ): HomeStore = HomeStore(interactor, appDispatcher, router)

    @Factory
    fun router(): HomeRouter = object : HomeRouter {
        override fun invoke(event: HomeStoreComponent.Navigation) {
            TODO("Not yet implemented")
        }
    }
}
