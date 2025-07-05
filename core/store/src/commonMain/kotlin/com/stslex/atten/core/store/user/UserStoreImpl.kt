package com.stslex.atten.core.store.user

import com.stslex.atten.core.store.types.StoreString
import com.stslex.atten.core.store.types.StoreString.Companion.string
import org.koin.core.annotation.Single
import org.koin.core.annotation.Singleton

@Single
@Singleton
class UserStoreImpl(
    private val userSettings: UserSettings
) : UserStore {

    override val accessToken: StoreString = userSettings.string(KEY_TOKEN)

    override val refreshToken: StoreString = userSettings.string(KEY_REFRESH_TOKEN)

    override val email: StoreString = userSettings.string(KEY_EMAIL)

    override val uuid: StoreString = userSettings.string(KEY_UUID)

    override fun clear() {
        userSettings.clear()
    }

    companion object {
        private const val KEY_TOKEN = "user.token"
        private const val KEY_REFRESH_TOKEN = "user.refresh_token"
        private const val KEY_EMAIL = "user.email"
        private const val KEY_UUID = "user.uuid"

        private const val EMPTY_VALUE = ""
    }
}