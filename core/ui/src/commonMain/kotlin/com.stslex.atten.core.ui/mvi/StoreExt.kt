package com.stslex.atten.core.ui.mvi

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.module.dsl.DefinitionOptions
import org.koin.core.module.dsl.new
import org.koin.core.module.dsl.onOptions
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

expect inline fun <reified T : ViewModel> Module.viewModelDefinition(
    qualifier: Qualifier? = null,
    noinline definition: Definition<T>
): KoinDefinition<T>

@Composable
expect inline fun <reified T : ViewModel> getViewModel(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): T

@Composable
inline fun <reified T : Store<*, *, *, *>> getStore(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): T = getViewModel(qualifier, parameters)

inline fun <reified T : Store<*, *, *, *>> Module.storeDefinition(
    qualifier: Qualifier? = null,
    noinline definition: Definition<T>
): KoinDefinition<T> = viewModelDefinition(qualifier, definition)

inline fun <reified R : Store<*, *, *, *>> Module.storeOf(
    crossinline constructor: () -> R,
    noinline options: DefinitionOptions<R>? = null,
): KoinDefinition<R> = storeDefinition { new(constructor) }.onOptions(options)

inline fun <reified R : Store<*, *, *, *>, reified T1> Module.storeOf(
    crossinline constructor: (T1) -> R,
    noinline options: DefinitionOptions<R>? = null,
): KoinDefinition<R> = storeDefinition { new(constructor) }.onOptions(options)

inline fun <reified R : Store<*, *, *, *>, reified T1, reified T2> Module.storeOf(
    crossinline constructor: (T1, T2) -> R,
    noinline options: DefinitionOptions<R>? = null,
): KoinDefinition<R> = storeDefinition { new(constructor) }.onOptions(options)

inline fun <reified R : Store<*, *, *, *>, reified T1, reified T2, reified T3> Module.storeOf(
    crossinline constructor: (T1, T2, T3) -> R,
    noinline options: DefinitionOptions<R>? = null,
): KoinDefinition<R> = storeDefinition { new(constructor) }.onOptions(options)

inline fun <reified R : Store<*, *, *, *>, reified T1, reified T2, reified T3, reified T4> Module.storeOf(
    crossinline constructor: (T1, T2, T3, T4) -> R,
    noinline options: DefinitionOptions<R>? = null,
): KoinDefinition<R> = storeDefinition { new(constructor) }.onOptions(options)

