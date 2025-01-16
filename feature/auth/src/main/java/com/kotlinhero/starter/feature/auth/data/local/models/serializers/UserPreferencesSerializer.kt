package com.kotlinhero.starter.feature.auth.data.local.models.serializers

import androidx.datastore.core.Serializer
import com.kotlinhero.starter.feature.auth.data.local.models.UserPreferences
import com.kotlinhero.starter.core.utils.parsePreference
import com.kotlinhero.starter.core.utils.writePreference
import java.io.InputStream
import java.io.OutputStream

object UserPreferencesSerializer : Serializer<com.kotlinhero.starter.feature.auth.data.local.models.UserPreferences> {
    override val defaultValue =
        com.kotlinhero.starter.feature.auth.data.local.models.UserPreferences()

    override suspend fun readFrom(input: InputStream) = input.parsePreference<com.kotlinhero.starter.feature.auth.data.local.models.UserPreferences>()

    override suspend fun writeTo(t: com.kotlinhero.starter.feature.auth.data.local.models.UserPreferences, output: OutputStream) {
        output.writePreference(t)
    }
}
