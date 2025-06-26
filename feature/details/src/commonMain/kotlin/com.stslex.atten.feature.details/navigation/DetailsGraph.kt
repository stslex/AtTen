package com.stslex.atten.feature.details.navigation

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import com.stslex.atten.core.ui.navigation.Screen
import com.stslex.atten.core.ui.navigation.navScreen
import com.stslex.atten.core.ui.kit.mvi.getStore
import com.stslex.atten.feature.details.ui.DetailsScreen
import com.stslex.atten.feature.details.ui.store.DetailsStore
import com.stslex.atten.feature.details.ui.store.DetailsStoreComponent.Action

fun NavGraphBuilder.detailsGraph() {
    navScreen<Screen.DetailScreen> { args ->
        val store = getStore<DetailsStore>()

        DisposableEffect(Unit) {
            store.dispatch(Action.Init(args.id))
            onDispose {
                store.dispatch(Action.OnScreenLeft)
            }
        }

        val state by remember(store) { store.state }.collectAsState()

        DetailsScreen(
            state = state,
            onTitleChange = { store.dispatch(Action.OnTitleValueChanged(it)) },
            onDescriptionChange = { store.dispatch(Action.OnDescriptionValueChanged(it)) },
            onSaveClicked = { store.dispatch(Action.OnSaveClicked) }
        )
    }
}
