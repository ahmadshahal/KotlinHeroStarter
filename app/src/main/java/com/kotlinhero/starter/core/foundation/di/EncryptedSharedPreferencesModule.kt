package com.kotlinhero.starter.core.foundation.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

private const val ENCRYPTED_SETTINGS_NAME = "encryptedSettings"

@Module
class EncryptedSharedPreferencesModule {

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
}