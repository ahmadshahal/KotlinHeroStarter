package com.kotlinhero.starter.core.foundation.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

private const val SETTINGS_NAME = "settings"

@Module
class PreferencesDataStoreModule {

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
}