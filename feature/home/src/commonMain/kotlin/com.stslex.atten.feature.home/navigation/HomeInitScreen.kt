package com.stslex.atten.feature.home.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import com.stslex.atten.core.ui.mvi.getStore
import com.stslex.atten.feature.home.ui.HomeScreen
import com.stslex.atten.feature.home.ui.store.HomeStore
import com.stslex.atten.feature.home.ui.store.HomeStoreComponent.Action
import com.stslex.atten.feature.home.ui.store.HomeStoreComponent.Event
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeInitScreen() {
    val store = getStore<HomeStore>()
    val state by remember { store.state }.collectAsState()
    val hapticFeedback = LocalHapticFeedback.current

    LaunchedEffect(Unit) {
        store.dispatch(Action.Init)

        store.event.collectLatest {
            when (it) {
                is Event.Snackbar -> {
                    // show snackbar
                }
            }
        }
    }

    HomeScreen(
        state = state,
        onItemClicked = { id ->
            hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            store.dispatch(Action.OnItemClicked(id))
        },
        onLoadNext = {
            store.dispatch(Action.LoadMore)
        },
        onCreateItemClick = {
            hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            store.dispatch(Action.OnCreateItemClicked)
        },
        onDeleteItemsClick = {
            hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            store.dispatch(Action.OnDeleteItemsClicked)
        },
        onItemLongCLick = { id ->
            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
            store.dispatch(Action.OnSelectItemClicked(id))
        }
    )
}
