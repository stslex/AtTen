package com.stslex.atten.core.ui.mvi

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import org.koin.compose.koinInject
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

expect inline fun <reified T : ViewModel> Module.viewModelDefinition(
    qualifier: Qualifier? = null,
    noinline definition: Definition<T>
): KoinDefinition<T>

inline fun <reified T : Store<*, *, *, *>> Module.storeDefinition(
    qualifier: Qualifier? = null,
    noinline definition: Definition<T>
): KoinDefinition<T> = viewModelDefinition(qualifier, definition)

@Composable
inline fun <reified T : Store<*, *, *, *>> getStore(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): T = koinInject(
    qualifier = qualifier,
    parameters = parameters
)
