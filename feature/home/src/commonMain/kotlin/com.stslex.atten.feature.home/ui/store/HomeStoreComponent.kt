package com.stslex.atten.feature.home.ui.store

import androidx.compose.runtime.Stable
import com.stslex.atten.core.paging.model.PagingConfig
import com.stslex.atten.core.paging.model.PagingUiState
import com.stslex.atten.core.ui.mvi.StoreComponent
import com.stslex.atten.feature.home.ui.model.TodoUiModel

interface HomeStoreComponent : StoreComponent {

    @Stable
    data class State(
        val query: String,
        val paging: PagingUiState<TodoUiModel>,
        val screen: ScreenState,
    ) : StoreComponent.State {

        companion object {
            val INIT = State(
                query = "",
                paging = PagingUiState.default(PagingConfig.DEFAULT),
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
        data object Init : Action

        @Stable
        data object LoadMore : Action

        @Stable
        data class OnItemClicked(val id: Long) : Action

        @Stable
        data class OnDeleteItemClicked(val id: Long) : Action

        @Stable
        data object OnCreateItemClicked : Action

        @Stable
        data object Refresh : Action

        @Stable
        data object Retry : Action
    }

    @Stable
    sealed interface Navigation : StoreComponent.Navigation {

        @Stable
        data class NavigateToDetail(val id: Long) : Navigation
    }
}