package com.kotlinhero.starter

import android.app.Application
import cafe.adriel.voyager.core.registry.ScreenRegistry
import com.kotlinhero.starter.core.CoreModule
import com.kotlinhero.starter.core.auth.di.UserDataStoreModule
import com.kotlinhero.starter.core.foundation.di.EncryptedSharedPreferencesModule
import com.kotlinhero.starter.core.foundation.di.KtorModule
import com.kotlinhero.starter.core.foundation.di.PreferencesDataStoreModule
import com.kotlinhero.starter.core.foundation.domain.flavors.BuildType
import com.kotlinhero.starter.feature.auth.AuthModule
import com.kotlinhero.starter.feature.auth.AuthNavigationModule
import com.kotlinhero.starter.settings.SettingsModule
import com.kotlinhero.starter.settings.SettingsNavigationModule
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
        ScreenRegistry {
            AuthNavigationModule()
            AppNavigationModule()
            SettingsNavigationModule()
        }
    }

    override fun onKoinStartup() = KoinConfiguration {
        androidLogger()
        androidContext(this@StarterApplication)
        modules(
            AuthModule().module,
            KtorModule().module,
            EncryptedSharedPreferencesModule().module,
            PreferencesDataStoreModule().module,
            CoreModule().module,
            AppModule().module,
            UserDataStoreModule().module,
            SettingsModule().module
        )
    }
}