package com.stslex.atten.feature.settings.mvi

import androidx.compose.runtime.Stable
import com.stslex.atten.core.ui.mvi.Store
import com.stslex.atten.feature.settings.mvi.SettingsStore.Action
import com.stslex.atten.feature.settings.mvi.SettingsStore.Event
import com.stslex.atten.feature.settings.mvi.SettingsStore.State

interface SettingsStore : Store<State, Action, Event> {

    @Stable
    data class State(
        val username: String = "",
    ) : Store.State {

        companion object {

            val INIT = State()
        }
    }

    @Stable
    sealed interface Action : Store.Action {

        @Stable
        sealed interface Lifecycle : Action {

            @Stable
            data object Init : Lifecycle

            @Stable
            data object Dispose : Lifecycle
        }

        @Stable
        sealed interface Click : Action {

            @Stable
            data object Back : Click

            @Stable
            data object Login : Click
        }

        @Stable
        sealed interface Navigation : Action {

            @Stable
            data object NavBack : Navigation
        }
    }

    @Stable
    sealed interface Event : Store.Event {

        data object GoogleAuth : Event
    }
}

