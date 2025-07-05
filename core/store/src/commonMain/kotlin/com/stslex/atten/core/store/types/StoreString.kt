package com.stslex.atten.core.store.types

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class StoreString(
    private val settings: Settings,
    private val key: String,
    defaultValue: String = EMPTY_VALUE
) {

    private val _flowValue = MutableStateFlow(settings.getString(key, defaultValue))
    val flowValue: StateFlow<String> = _flowValue.asStateFlow()

    var value: String
        get() = flowValue.value
        set(value) {
            settings[key] = value
            _flowValue.value = value
        }

    companion object {

        private const val EMPTY_VALUE = ""

        internal fun Settings.string(
            key: String,
            defaultValue: String = EMPTY_VALUE
        ): StoreString = StoreString(
            settings = this,
            key = key,
            defaultValue = defaultValue
        )
    }
}