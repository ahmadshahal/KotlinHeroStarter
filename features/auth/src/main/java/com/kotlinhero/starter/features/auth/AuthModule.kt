package com.kotlinhero.starter.features.auth

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.kotlinhero.starter.features.auth.data.local.models.UserPreferences
import com.kotlinhero.starter.features.auth.data.local.models.serializers.UserPreferencesSerializer
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

private const val USER_SETTINGS_NAME = "userSettings"

@Module
@ComponentScan
class AuthModule {

    @Single
    @Named("UserDataStore")
    fun provideUserDataStore(
        applicationContext: Context
    ): DataStore<UserPreferences> {
        return DataStoreFactory.create(
            serializer = UserPreferencesSerializer,
            produceFile = { applicationContext.dataStoreFile(USER_SETTINGS_NAME) },
        )
    }
}
