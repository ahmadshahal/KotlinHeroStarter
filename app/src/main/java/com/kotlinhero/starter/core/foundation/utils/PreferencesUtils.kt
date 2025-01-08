package com.kotlinhero.starter.core.foundation.utils

import androidx.datastore.core.CorruptionException
import java.io.InputStream
import java.io.OutputStream
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified T> InputStream.parsePreference(): T {
    try {
        return Json.decodeFromString(readBytes().decodeToString())
    } catch (serialization: SerializationException) {
        throw CorruptionException("Unable to read Object", serialization)
    }
}

inline fun <reified T> OutputStream.writePreference(t: T) =
    write(Json.encodeToString(t).encodeToByteArray())
