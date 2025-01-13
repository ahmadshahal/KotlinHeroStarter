package com.kotlinhero.starter.core.foundation.di

import com.kotlinhero.starter.BuildConfig
import com.kotlinhero.starter.core.auth.data.remote.interceptors.AuthInterceptor
import com.kotlinhero.starter.core.foundation.data.remote.interceptors.LanguageInterceptor
import com.kotlinhero.starter.core.foundation.domain.flavors.Flavor
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
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
                val loggingInterceptor = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }

                config { followRedirects(true) }
                addInterceptor(AuthInterceptor())
                addInterceptor(LanguageInterceptor())
                addInterceptor(loggingInterceptor)
            }

            defaultRequest {
                url(Flavor.current().baseUrl)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header("platform", "Android")
                header("version", BuildConfig.VERSION_NAME)

                // TODO: Add it as an interceptor?
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
        }
    }
}