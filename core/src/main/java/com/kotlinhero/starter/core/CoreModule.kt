package com.kotlinhero.starter.core

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.kotlinhero.starter.core.data.remote.interceptors.LanguageInterceptor
import com.kotlinhero.starter.core.domain.flavors.Flavor
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

private const val ENCRYPTED_SETTINGS_NAME = "encryptedSettings"
private const val SETTINGS_NAME = "settings"

@Module
@ComponentScan
class CoreModule(private val okHttpInterceptors: List<Interceptor>) {

    @Single
    fun providePreferencesDataStore(
        applicationContext: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = {
                applicationContext.preferencesDataStoreFile(SETTINGS_NAME)
            },
        )
    }

    @Single
    fun provideEncryptedSharedPreferences(applicationContext: Context): SharedPreferences {
        return EncryptedSharedPreferences.create(
            applicationContext,
            ENCRYPTED_SETTINGS_NAME,
            MasterKey.Builder(applicationContext)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Single
    fun provideKtor(@Named("OkHttpInterceptors") interceptors: List<Interceptor> = emptyList()): HttpClient {
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

                okHttpInterceptors.forEach { addInterceptor(it) }
                addInterceptor(LanguageInterceptor())
                addInterceptor(loggingInterceptor)
            }

            defaultRequest {
                url(Flavor.current().baseUrl)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header("platform", "Android")
                header("version", BuildConfig.VERSION_NAME)
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