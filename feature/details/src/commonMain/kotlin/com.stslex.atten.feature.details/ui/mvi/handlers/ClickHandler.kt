package com.stslex.atten.feature.details.ui.mvi.handlers

import com.stslex.atten.core.ui.mvi.handler.Handler
import com.stslex.atten.feature.details.di.DetailsScope
import com.stslex.atten.feature.details.domain.interactor.DetailsInteractor
import com.stslex.atten.feature.details.ui.model.toDomain
import com.stslex.atten.feature.details.ui.mvi.DetailsHandlerStore
import com.stslex.atten.feature.details.ui.mvi.DetailsStore.Action
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Scoped

@Factory
@Scope(DetailsScope::class)
@Scoped
internal class ClickHandler(
    private val interactor: DetailsInteractor,
) : Handler<Action.Click, DetailsHandlerStore> {

    override fun DetailsHandlerStore.invoke(action: Action.Click) {
        when (action) {
            Action.Click.OnBackClicked -> actionOnBackClicked()
            Action.Click.OnSaveClicked -> actionOnSaveClicked()
        }
    }

    private fun DetailsHandlerStore.actionOnBackClicked() {
        if (
            state.value.item.title.isEmpty() &&
            state.value.item.description.isEmpty()
        ) {
            launch(
                action = {
                    interactor.removeItem(state.value.item.uuid)
                },
                eachDispatcher = appDispatcher.immediate,
                onSuccess = { consume(Action.Navigation.Back) },
                onError = { error -> consume(Action.Common.OnError(error)) }
            )
        } else {
            consume(Action.Navigation.Back)
        }
    }

    private fun DetailsHandlerStore.actionOnSaveClicked() {
        launch(
            action = {
                state.value.let { currentState ->
                    val item = currentState.item.toDomain(currentState.createdAt)
                        ?: throw IllegalStateException("current created at is invalid")
                    interactor.updateItem(item)
                }
            },
            eachDispatcher = appDispatcher.immediate,
            onSuccess = { consume(Action.Navigation.Back) },
            onError = { error -> consume(Action.Common.OnError(error)) }
        )
    }
}