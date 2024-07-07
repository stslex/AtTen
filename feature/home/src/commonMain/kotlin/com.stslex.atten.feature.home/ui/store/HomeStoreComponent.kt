package com.stslex.atten.feature.home.ui.store

import androidx.compose.runtime.Stable
import com.stslex.atten.core.paging.model.PagingConfig
import com.stslex.atten.core.paging.states.PagingUiState
import com.stslex.atten.core.ui.mvi.StoreComponent
import com.stslex.atten.feature.home.ui.model.TodoUiModel
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf

interface HomeStoreComponent : StoreComponent {

    @Stable
    data class State(
        val query: String,
        val paging: PagingUiState<TodoUiModel>,
        val screen: ScreenState,
        val selectedItems: ImmutableSet<String>
    ) : StoreComponent.State {

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
        data class OnItemClicked(val id: String) : Action

        @Stable
        data object OnDeleteItemsClicked : Action

        @Stable
        data class OnSelectItemClicked(val id: String) : Action

        @Stable
        data object OnCreateItemClicked : Action

        @Stable
        data object Refresh : Action

        @Stable
        data object Retry : Action

        @Stable
        data object OnBackPressed : Action
    }

    @Stable
    sealed interface Navigation : StoreComponent.Navigation {

        @Stable
        data class NavigateToDetail(val id: String) : Navigation

        @Stable
        data object Back : Navigation
    }
}