package com.stslex.atten.host

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.navigate
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.Value
import com.stslex.atten.feature.settings.mvi.SettingsComponent
import com.stslex.atten.core.ui.navigation.Config
import com.stslex.atten.core.ui.navigation.Router
import com.stslex.atten.feature.details.ui.mvi.DetailsComponent
import com.stslex.atten.feature.home.ui.mvi.HomeComponent
import com.stslex.atten.host.RootComponent.Child

class DefaultRootComponent(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    private val _stack = childStack(
        source = navigation,
        serializer = Config.serializer(),
        childFactory = ::child,
        handleBackButton = true,
        initialConfiguration = Config.Home
    )

    override val stack: Value<ChildStack<Config, Child>> = _stack

    override fun onConfigChanged(block: (Config) -> Unit) = stack.subscribe {
        block(it.active.configuration)
    }

    private fun child(
        config: Config,
        context: ComponentContext
    ): Child = when (config) {
        is Config.Home -> Child.Home(HomeComponent.create(context.router))
        is Config.Detail -> Child.Details(DetailsComponent.create(context.router, config.uuid))
        is Config.Settings -> Child.Settings(SettingsComponent.create(context.router))
    }

    @OptIn(DelicateDecomposeApi::class)
    private fun navigateTo(config: Config) {
        navigation.navigate { currentStack ->
            if (config.isBackAllow) {
                currentStack + config
            } else {
                listOf(config)
            }
        }
    }

    private fun popBack() {
        navigation.pop()
    }

    private val ComponentContext.router: Router
        get() = object : Router, ComponentContext by this {

            override fun navTo(config: Config) {
                navigateTo(config)
            }

            override fun popBack() {
                this@DefaultRootComponent.popBack()
            }
        }
}
