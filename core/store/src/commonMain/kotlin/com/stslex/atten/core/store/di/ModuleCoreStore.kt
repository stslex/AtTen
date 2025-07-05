package com.stslex.atten.core.store.di

import com.stslex.atten.core.store.user.UserSettings
import com.stslex.atten.core.store.user.createUserSettings
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.core.annotation.Singleton
import org.koin.core.scope.Scope

@Module
@ComponentScan("com.stslex.atten.core.store")
@Singleton
class ModuleCoreStore {

    @Single
    @Singleton
    fun userSettings(scope: Scope): UserSettings = scope.createUserSettings()
}
