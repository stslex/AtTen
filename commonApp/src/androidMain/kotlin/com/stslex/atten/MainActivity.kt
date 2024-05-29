package com.stslex.atten

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.retainedComponent
import com.stslex.atten.core.common.isDebug
import com.stslex.atten.core.navigation.decompose.RootComponent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val root = retainedComponent { RootComponent(it) }
            App(root) {
                androidLogger(
                    level = if (isDebug) {
                        Level.DEBUG
                    } else {
                        Level.NONE
                    }
                )
                androidContext(this@MainActivity)
            }
        }
    }
}

