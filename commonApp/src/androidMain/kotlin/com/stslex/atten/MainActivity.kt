package com.stslex.atten

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.defaultComponentContext
import com.stslex.atten.core.ui.kit.utils.ActivityHolderProducer
import com.stslex.atten.host.DefaultRootComponent
import org.koin.android.ext.android.getKoin

class MainActivity : ComponentActivity() {

    private val activityProducer: ActivityHolderProducer by lazy { getKoin().get() }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val rootComponent = DefaultRootComponent(defaultComponentContext())
        val windowController = WindowCompat.getInsetsController(window, window.decorView)

        activityProducer.produce(this)
        setContent {
            App(
                rootComponent = rootComponent,
                onThemeChange = { isDarkTheme ->
                    windowController.isAppearanceLightStatusBars = isDarkTheme.not()
                }
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activityProducer.produce(null)
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(
        rootComponent = DefaultRootComponent(
            componentContext = DefaultComponentContext(LocalLifecycleOwner.current.lifecycle),
        ),
    )
}
