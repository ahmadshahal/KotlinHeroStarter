package com.kotlinhero.starter.core.di

import com.kotlinhero.starter.BuildConfig
import com.kotlinhero.starter.core.data.remote.models.ErrorResponseBody
import com.kotlinhero.starter.core.domain.flavors.Flavor
import com.kotlinhero.starter.core.utils.exceptions.RemoteException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import timber.log.Timber
import java.util.Locale

@Module
class KtorModule {

    @Single
    fun provideKtor(): HttpClient {
        return HttpClient(OkHttp) {
            expectSuccess = true

            /*
            HttpResponseValidator {
                handleResponseExceptionWithRequest { exception, _ ->
                    val clientException =
                        exception as? ClientRequestException
                            ?: return@handleResponseExceptionWithRequest

                    val exceptionResponse = clientException.response.body<ErrorResponseBody>()
                    throw RemoteException(
                        message = exceptionResponse.details.firstOrNull() ?: "",
                        code = clientException.response.status.value
                    )
                }
            }
             */

            engine {
                config {
                    followRedirects(true)
                }

                /*
                addInterceptor(interceptor)
                addNetworkInterceptor(interceptor)
                 */
            }

            defaultRequest {
                url(Flavor.current().baseUrl)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header("platform", "Android")
                header("version", BuildConfig.VERSION_NAME)
                header("language", Locale.getDefault().language)
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.d("Logger Ktor => $message")
                    }
                }
                level = LogLevel.ALL
            }

            Auth {
                bearer {
                    loadTokens {
                        BearerTokens(
                            accessToken = "",
                            refreshToken = ""
                        )
                    }
                    refreshTokens {
                        BearerTokens(
                            accessToken = "",
                            refreshToken = ""
                        )
                    }
                }
            }
        }
    }
}