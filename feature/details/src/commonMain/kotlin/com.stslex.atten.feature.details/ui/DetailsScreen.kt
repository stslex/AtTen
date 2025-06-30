package com.stslex.atten.feature.details.ui

import androidx.compose.runtime.Composable
import com.stslex.atten.core.ui.mvi.NavComponentScreen
import com.stslex.atten.feature.details.di.DetailsFeature
import com.stslex.atten.feature.details.ui.mvi.DetailsComponent
import com.stslex.atten.feature.details.ui.mvi.DetailsStore.Action

@Composable
fun DetailsScreen(
    component: DetailsComponent
) {
    NavComponentScreen(DetailsFeature, component) { processor ->
        DetailsWidget(
            state = processor.state.value,
            onTitleChange = { processor.consume(Action.Input.Title(it)) },
            onDescriptionChange = { processor.consume(Action.Input.Description(it)) },
            onSaveClicked = { processor.consume(Action.Click.OnSaveClicked) }
        )
    }
}