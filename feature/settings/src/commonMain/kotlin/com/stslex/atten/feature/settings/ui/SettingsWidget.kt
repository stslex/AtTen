package com.stslex.atten.feature.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stslex.atten.feature.settings.mvi.SettingsStore.Action
import com.stslex.atten.feature.settings.mvi.SettingsStore.State
import com.stslex.atten.feature.settings.ui.components.SettingsTopbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingsWidget(
    state: State,
    consume: (Action) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            SettingsTopbar(
                title = state.username,
                onBackClick = { consume(Action.Click.Back) },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(
                text = "Settings Screen",
                style = MaterialTheme.typography.bodyLarge,
            )

            Button(
                onClick = { consume(Action.Click.Login) },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Login")
            }

        }
    }
}