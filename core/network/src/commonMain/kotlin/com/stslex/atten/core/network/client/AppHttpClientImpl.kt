package com.stslex.atten.core.network.client

import AtTen.core.network.BuildConfig
import com.stslex.atten.core.network.KtorLogger
import com.stslex.atten.core.network.error_handler.ErrorHandler
import com.stslex.atten.core.store.user.UserStore
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.CIOEngineConfig
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single
import org.koin.core.annotation.Singleton

@Single
@Singleton
internal class AppHttpClientImpl(
    errorHandler: ErrorHandler,
    private val userStore: UserStore,
) : AppHttpClient {

    override val client = HttpClient(CIO) {
        install(HttpCache)
        expectSuccess = true
        HttpResponseValidator { handleResponseExceptionWithRequest(errorHandler) }
        setupDefaultRequest()
        setupNegotiation()
        setupLogging()
    }

    private fun HttpClientConfig<CIOEngineConfig>.setupNegotiation() {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    explicitNulls = false
                }
            )
        }
    }

    private fun HttpClientConfig<CIOEngineConfig>.setupLogging() {
        install(Logging) {
            // TODO dev type log
            logger = KtorLogger
            level = LogLevel.ALL
        }
    }

    private fun HttpClientConfig<*>.installAuth() {
        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens(
                        accessToken = userStore.accessToken.value,
                        refreshToken = userStore.refreshToken.value
                    )
                }
            }
        }
    }

    private fun HttpClientConfig<*>.setupDefaultRequest() {
        defaultRequest {
            url(
                scheme = URLProtocol.HTTP.name,
                host = BuildConfig.SERVER_HOST,
                port = BuildConfig.SERVER_PORT.toInt(),
                path = BuildConfig.SERVER_API_VERSION,
                block = {
                    contentType(ContentType.Application.Json)
                }
            )
            headers {
                append(
                    API_KEY_NAME,
                    BuildConfig.SERVER_API_KEY
                )
            }
        }
    }

    companion object {
        private const val API_KEY_NAME = "X-Api-Key"
    }
}