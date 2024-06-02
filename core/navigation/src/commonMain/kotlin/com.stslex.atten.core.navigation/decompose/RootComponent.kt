package com.stslex.atten.core.navigation.decompose

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.backStack
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.stslex.atten.core.navigation.di.ModuleCoreNavigation
import com.stslex.atten.core.navigation.screen.Configuration
import org.koin.compose.getKoin

class RootComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    private val navigation = StackNavigation<Configuration>()

    internal val childStack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.HomeScreen,
        handleBackButton = true,
        childFactory = ::createChild
    )

    fun onBack() {
        navigation.pop()
    }

    private fun  createChild(
        config: Configuration,
        context: ComponentContext,
    ): Child<*> = when (config) {
        Configuration.HomeScreen -> Child.HomeScreen(AppComponents.HomeComponent(context))
        is Configuration.DetailScreen -> Child.DetailScreen(
            AppComponents.DetailComponent(
                id = config.id,
                context = context,
            )
        )
    }

    @Composable
    internal fun bindNavigator() {
        getKoin().loadModules(listOf(ModuleCoreNavigation().create(navigation)))
    }
}