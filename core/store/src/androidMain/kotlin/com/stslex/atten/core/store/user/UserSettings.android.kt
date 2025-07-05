package com.stslex.atten.core.store.user

import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import com.stslex.atten.core.store.createEncriptedSharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.Scope

internal actual fun Scope.createUserSettings(): UserSettings {
    val delegate = createEncriptedSharedPreferences(
        context = androidContext(),
        name = UserSettings.NAME
    )
    val userSettings = SharedPreferencesSettings(delegate)
    return object : UserSettings, Settings by userSettings {}
}