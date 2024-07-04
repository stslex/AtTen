package com.stslex.atten.feature.home.ui.store

import androidx.compose.runtime.Stable
import app.cash.paging.PagingData
import com.stslex.atten.core.ui.mvi.StoreComponent
import com.stslex.atten.feature.home.ui.model.TodoUiModel
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.coroutines.flow.Flow

interface HomeStoreComponent : StoreComponent {

    @Stable
    data class State(
        val query: String = "",
        val paging: () -> Flow<PagingData<TodoUiModel>>,
        val screen: ScreenState = ScreenState.Shimmer,
        val selectedItems: ImmutableSet<String> = persistentSetOf()
    ) : StoreComponent.State {

        companion object {
            fun default(
                paging: () -> Flow<PagingData<TodoUiModel>>
            ) = State(
                query = "",
                paging = paging,
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