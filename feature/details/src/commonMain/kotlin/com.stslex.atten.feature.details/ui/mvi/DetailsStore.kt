package com.stslex.atten.feature.details.ui.mvi

import androidx.compose.runtime.Stable
import com.stslex.atten.core.ui.mvi.CommonEvents
import com.stslex.atten.core.ui.mvi.Store
import com.stslex.atten.feature.details.ui.model.ToDoDetailsUIModel
import com.stslex.atten.feature.details.ui.mvi.DetailsStore.Action
import com.stslex.atten.feature.details.ui.mvi.DetailsStore.Event
import com.stslex.atten.feature.details.ui.mvi.DetailsStore.State
import kotlinx.datetime.LocalDateTime

interface DetailsStore : Store<State, Action, Event> {

    @Stable
    data class State(
        val item: ToDoDetailsUIModel,
        val screen: ScreenState,
        val createdAt: LocalDateTime?
    ) : Store.State {

        companion object {

            val INIT = State(
                item = ToDoDetailsUIModel(
                    uuid = "",
                    title = "",
                    description = "",
                ),
                screen = ScreenState.Shimmer,
                createdAt = null
            )
        }
    }

    @Stable
    sealed interface Event : Store.Event {

        @Stable
        data class Snackbar(
            val snackbar: CommonEvents.Snackbar
        ) : Event
    }

    @Stable
    sealed interface Action : Store.Action {

        @Stable
        sealed interface Input : Action {

            @Stable
            data class Title(val title: String) : Input

            @Stable
            data class Description(val description: String) : Input
        }

        @Stable
        sealed interface Common : Action {

            @Stable
            data class InitialLoad(val uuid: String) : Common

            @Stable
            data object OnScreenLeft : Common

            @Stable
            data class OnError(val error: Throwable) : Common
        }

        @Stable
        sealed interface Click : Action {

            @Stable
            data object OnSaveClicked : Click

            @Stable
            data object OnBackClicked : Click
        }

        sealed interface Navigation : Action, Store.Action.Navigation {

            data object Back : Navigation
        }
    }
}