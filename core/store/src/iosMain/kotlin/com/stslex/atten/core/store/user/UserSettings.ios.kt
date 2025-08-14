package com.stslex.atten.core.store.user

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import org.koin.core.scope.Scope
import platform.Foundation.NSUserDefaults

internal actual fun Scope.createUserSettings(): UserSettings {
    val delegate = NSUserDefaults(suiteName = UserSettings.NAME)
    val settings: Settings = NSUserDefaultsSettings(delegate)
    return object : UserSettings, Settings by settings {}
}