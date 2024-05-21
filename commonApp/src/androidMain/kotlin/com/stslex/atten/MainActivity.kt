package com.stslex.atten

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            AndroidApp()
        }
    }
}

@Composable
private fun AndroidApp() {
    App()
}

@Preview
@Composable
private fun AndroidAppPreview() {
    AndroidApp()
}