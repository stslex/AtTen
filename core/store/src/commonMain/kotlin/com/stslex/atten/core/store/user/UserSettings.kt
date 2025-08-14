package com.stslex.atten.core.store.user

import com.russhwolf.settings.Settings
import org.koin.core.scope.Scope

interface UserSettings : Settings {

    companion object {

        const val NAME = "user.settings"
    }
}

internal expect fun Scope.createUserSettings(): UserSettings
