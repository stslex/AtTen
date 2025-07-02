package com.stslex.atten.feature.home.ui.mvi

import androidx.compose.runtime.Stable
import com.stslex.atten.core.paging.model.PagingConfig
import com.stslex.atten.core.paging.states.PagingUiState
import com.stslex.atten.core.ui.mvi.CommonEvents
import com.stslex.atten.core.ui.mvi.Store
import com.stslex.atten.feature.home.ui.model.TodoUiModel
import com.stslex.atten.feature.home.ui.mvi.HomeStore.Action
import com.stslex.atten.feature.home.ui.mvi.HomeStore.Event
import com.stslex.atten.feature.home.ui.mvi.HomeStore.State
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf

interface HomeStore : Store<State, Action, Event> {

    @Stable
    data class State(
        val query: String,
        val paging: PagingUiState<TodoUiModel>,
        val screen: ScreenState,
        val selectedItems: ImmutableSet<String>
    ) : Store.State {

        companion object {

            val INIT = State(
                query = "",
                paging = PagingUiState.default(
                    PagingConfig(
                        pageSize = 30,
                        pageOffset = 1f
                    )
                ),
                screen = ScreenState.Shimmer,
                selectedItems = persistentSetOf()
            )
        }
    }

    @Stable
    sealed interface Event : Store.Event {

        @Stable
        data class Snackbar(
            val snackbar: CommonEvents.Snackbar
        ) : Event

        @Stable
        sealed interface List : Event {

            data object ScrollTop : List
        }

        sealed interface Haptic : Event {

            data object Click : Haptic

            data object LongClick : Haptic
        }
    }

    @Stable
    sealed interface Action : Store.Action {

        sealed interface Paging : Action {

            @Stable
            data object Init : Paging

            @Stable
            data object LoadMore : Paging

            @Stable
            data object Refresh : Paging

            @Stable
            data object Retry : Paging
        }

        @Stable
        sealed interface Common : Action {

            @Stable
            data class ProcessError(val error: Throwable) : Common
        }

        @Stable
        sealed interface Click : Action {

            @Stable
            data object OnBackPressed : Click

            @Stable
            data class OnItemClicked(val id: String) : Click

            @Stable
            data object OnDeleteItemsClicked : Click

            @Stable
            data class OnSelectItemClicked(val id: String) : Click

            @Stable
            data object OnCreateItemClicked : Click

            @Stable
            data object OnSettingsClicked : Click
        }

        @Stable
        sealed interface Navigation : Action, Store.Action.Navigation {

            @Stable
            data class NavigateToDetail(val id: String) : Navigation

            @Stable
            data object Settings : Navigation

            @Stable
            data object Back : Navigation
        }
    }
}