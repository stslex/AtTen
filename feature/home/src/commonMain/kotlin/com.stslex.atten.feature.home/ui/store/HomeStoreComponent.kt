package com.stslex.atten.feature.home.ui.store

import androidx.compose.runtime.Stable
import com.stslex.atten.core.ui.mvi.StoreComponent

interface HomeStoreComponent : StoreComponent {

    @Stable
    data class State(
        val items: List<TodoUiModel>,
        val state: ScreenState
    ) : StoreComponent.State {

        companion object {
            val INIT = State(
                items = emptyList(),
                state = ScreenState.Shimmer
            )

            const val PAGE_SIZE = 10
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
        data object Init : Action

        @Stable
        data object LoadMore : Action

        @Stable
        data class OnItemClicked(val id: Long) : Action
    }

    @Stable
    sealed interface Navigation : StoreComponent.Navigation {

        @Stable
        data class NavigateToDetail(val id: Long) : Navigation
    }
}