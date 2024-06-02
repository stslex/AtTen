package com.stslex.atten.feature.details.ui.store

import androidx.compose.runtime.Stable
import com.stslex.atten.core.ui.mvi.StoreComponent
import com.stslex.atten.feature.details.ui.model.ToDoDetailsUIModel

interface DetailsStoreComponent : StoreComponent {

    @Stable
    data class State(
        val item: ToDoDetailsUIModel,
        val screen: ScreenState
    ) : StoreComponent.State {

        companion object {
            val INIT = State(
                item = ToDoDetailsUIModel(
                    uuid = "",
                    title = "",
                    description = "",
                ),
                screen = ScreenState.Shimmer
            )
        }
    }

    @Stable
    sealed interface Event : StoreComponent.Event {

        @Stable
        data class Snackbar(
            val snackbar: StoreComponent.Event.Snackbar
        ) : Event
    }

    @Stable
    sealed interface Action : StoreComponent.Action {

        @Stable
        data class Init(val uuid: String) : Action

        @Stable
        data object OnBackClicked : Action

        @Stable
        data class OnTitleValueChanged(val title: String) : Action

        @Stable
        data class OnDescriptionValueChanged(val description: String) : Action

        @Stable
        data object OnScreenLeft : Action

        @Stable
        data object OnSaveClicked : Action
    }

    sealed interface Navigation : StoreComponent.Navigation {

        data object Back : Navigation
    }
}