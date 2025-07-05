package com.stslex.atten.core.network.client

import io.ktor.client.HttpClient

interface AppHttpClient {

    val client: HttpClient
}

