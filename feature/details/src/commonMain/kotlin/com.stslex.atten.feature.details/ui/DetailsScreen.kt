package com.stslex.atten.feature.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.stslex.atten.core.ui.theme.AppDimension
import com.stslex.atten.feature.details.ui.store.DetailsStoreComponent.State

@Composable
internal fun DetailsScreen(
    state: State,
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
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.item.title,
            onValueChange = onTitleChange,
            label = { Text("title") }
        )
        Spacer(modifier = Modifier.height(AppDimension.Padding.medium))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.item.description,
            onValueChange = onDescriptionChange,
            label = { Text("description") }
        )

        Button(
            onClick = onSaveClicked,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Save")
        }
    }
}