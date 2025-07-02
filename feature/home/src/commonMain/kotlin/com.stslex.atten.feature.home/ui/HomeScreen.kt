package com.stslex.atten.feature.home.ui

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import com.stslex.atten.core.ui.mvi.NavComponentScreen
import com.stslex.atten.feature.home.di.HomeFeature
import com.stslex.atten.feature.home.ui.mvi.HomeComponent
import com.stslex.atten.feature.home.ui.mvi.HomeStore
import com.stslex.atten.feature.home.ui.mvi.HomeStore.Action

@Composable
fun HomeScreen(component: HomeComponent) {
    NavComponentScreen(HomeFeature, component) { processor ->
        val hapticFeedback = LocalHapticFeedback.current
        val lazyListState = rememberLazyListState()
        processor.handle { event ->
            when (event) {
                is HomeStore.Event.Snackbar -> Unit

                is HomeStore.Event.List.ScrollTop -> lazyListState.requestScrollToItem(0)

                HomeStore.Event.Haptic.Click -> {
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                }

                HomeStore.Event.Haptic.LongClick -> {
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                }
            }
        }

        // todo: add back handler
//        AppBackHandler(
//            enabled = state.selectedItems.isNotEmpty(),
//            onBack = {
//                store.dispatch(Action.OnBackPressed)
//            }
//        )

        HomeWidget(
            state = processor.state.value,
            lazyListState = lazyListState,
            onItemClicked = { id ->
                processor.consume(Action.Click.OnItemClicked(id))
            },
            onLoadNext = {
                processor.consume(Action.Paging.LoadMore)
            },
            onCreateItemClick = {
                processor.consume(Action.Click.OnCreateItemClicked)
            },
            onDeleteItemsClick = {
                processor.consume(Action.Click.OnDeleteItemsClicked)
            },
            onItemLongCLick = { id ->
                processor.consume(Action.Click.OnSelectItemClicked(id))
            },
            onSettingsClick = {
                processor.consume(Action.Click.OnSettingsClicked)
            }
        )
    }
}