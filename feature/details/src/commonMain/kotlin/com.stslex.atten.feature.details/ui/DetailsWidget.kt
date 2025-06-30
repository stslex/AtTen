package com.stslex.atten.feature.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.stslex.atten.core.ui.kit.theme.AppDimension
import com.stslex.atten.core.ui.kit.theme.AppTheme
import com.stslex.atten.feature.details.ui.components.DescriptionTextField
import com.stslex.atten.feature.details.ui.components.TitleTextField
import com.stslex.atten.feature.details.ui.model.ToDoDetailsUIModel
import com.stslex.atten.feature.details.ui.mvi.DetailsStore
import com.stslex.atten.feature.details.ui.mvi.ScreenState
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun DetailsWidget(
    state: DetailsStore.State,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onSaveClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
            .padding(AppDimension.Padding.medium)
    ) {
        TitleTextField(
            title = state.item.title,
            onTitleChange = onTitleChange
        )
        Spacer(modifier = Modifier.height(AppDimension.Padding.medium))
        DescriptionTextField(
            modifier = Modifier.weight(1f),
            description = state.item.description,
            onDescriptionChange = onDescriptionChange
        )
        Button(
            onClick = onSaveClicked,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Save")
        }
    }
}

@Composable
@Preview
internal fun DetailsWidgetPreview() {
    AppTheme {
        DetailsWidget(
            state = DetailsStore.State(
                item = ToDoDetailsUIModel(
                    uuid = "uuid",
                    title = "Title",
                    description = "Description"
                ),
                screen = ScreenState.Content,
                createdAt = null
            ),
            onTitleChange = {},
            onDescriptionChange = {},
            onSaveClicked = {}
        )
    }
}