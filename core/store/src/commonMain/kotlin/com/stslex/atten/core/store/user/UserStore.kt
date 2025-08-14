package com.stslex.atten.core.store.user

import com.stslex.atten.core.store.types.StoreString

interface UserStore {

    val accessToken: StoreString

    val refreshToken: StoreString

    val email: StoreString

    val uuid: StoreString

    fun clear()
}
