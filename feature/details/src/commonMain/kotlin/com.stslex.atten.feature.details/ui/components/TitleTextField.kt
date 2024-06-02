package com.stslex.atten.feature.details.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun TitleTextField(
    title: String,
    onTitleChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = title,
        onValueChange = onTitleChange,
        label = { Text("title") },
        maxLines = 1,
        singleLine = true,
        textStyle = MaterialTheme.typography.headlineMedium,
    )
}
