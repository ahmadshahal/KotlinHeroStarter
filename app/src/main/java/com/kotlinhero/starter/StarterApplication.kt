package com.kotlinhero.starter

import android.app.Application
import com.kotlinhero.starter.core.CoreModule
import com.kotlinhero.starter.core.domain.flavors.BuildType
import com.kotlinhero.starter.feature.auth.AuthModule
import com.kotlinhero.starter.feature.auth.data.remote.interceptors.AuthInterceptor
import com.kotlinhero.starter.settings.SettingsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androix.startup.KoinStartup
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.KoinConfiguration
import org.koin.ksp.generated.module
import timber.log.Timber

@OptIn(KoinExperimentalAPI::class)
class StarterApplication : Application(), KoinStartup {
    override fun onCreate() {
        super.onCreate()
        if (BuildType.current() == BuildType.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun onKoinStartup() = KoinConfiguration {
        androidLogger()
        androidContext(this@StarterApplication)

        // Question: Is it valid to provide the AuthInterceptor here?
        val okHttpInterceptors = listOf(AuthInterceptor())

        modules(
            AppModule().module,
            CoreModule(okHttpInterceptors).module,
            AuthModule().module,
            SettingsModule().module
        )
    }
}