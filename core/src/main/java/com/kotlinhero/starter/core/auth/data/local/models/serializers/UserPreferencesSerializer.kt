package com.kotlinhero.starter.core.auth.data.local.models.serializers

import androidx.datastore.core.Serializer
import com.kotlinhero.starter.core.auth.data.local.models.UserPreferences
import com.kotlinhero.starter.core.foundation.utils.parsePreference
import com.kotlinhero.starter.core.foundation.utils.writePreference
import java.io.InputStream
import java.io.OutputStream

object UserPreferencesSerializer : Serializer<UserPreferences> {
    override val defaultValue = UserPreferences()

    override suspend fun readFrom(input: InputStream) = input.parsePreference<UserPreferences>()

    override suspend fun writeTo(t: UserPreferences, output: OutputStream) {
        output.writePreference(t)
    }
}
