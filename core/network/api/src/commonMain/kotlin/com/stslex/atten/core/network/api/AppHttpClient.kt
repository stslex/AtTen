package com.stslex.atten.core.network.api

import io.ktor.client.HttpClient

interface AppHttpClient {

    val client: HttpClient
}