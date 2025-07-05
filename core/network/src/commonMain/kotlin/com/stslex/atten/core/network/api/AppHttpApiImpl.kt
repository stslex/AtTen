package com.stslex.atten.core.network.api

import com.stslex.atten.core.core.coroutine.dispatcher.AppDispatcher
import com.stslex.atten.core.network.client.AppHttpClient
import io.ktor.client.HttpClient
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single
import org.koin.core.annotation.Singleton

@Single
@Singleton
class AppHttpApiImpl(
    private val appDispatcher: AppDispatcher,
    private val appHttpClient: AppHttpClient
) : AppHttpApi {

    override suspend fun <T> request(
        block: suspend HttpClient.() -> T
    ): T = withContext(appDispatcher.io) {
        block(appHttpClient.client)
    }
}