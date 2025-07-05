package com.stslex.atten.core.network.api

import io.ktor.client.HttpClient

interface AppHttpApi {

    suspend fun <T> request(block: suspend HttpClient.() -> T): T
}
