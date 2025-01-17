package com.kotlinhero.starter

import android.app.Application
import com.kotlinhero.starter.core.CoreModule
import com.kotlinhero.starter.core.domain.flavors.BuildType
import com.kotlinhero.starter.features.SettingsModule
import com.kotlinhero.starter.features.auth.AuthModule
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
        modules(
            AppModule().module,
            CoreModule().module,
            AuthModule().module,
            SettingsModule().module
        )
    }
}