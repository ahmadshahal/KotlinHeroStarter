package com.kotlinhero.starter.features.auth.domain.entities

import kotlinx.serialization.Serializable

/**
 * Wrapper class for encrypted data and its associated initialization vector (IV).
 */
@Serializable
data class CipherText(
    val ciphertext: ByteArray,
    val initializationVector: ByteArray
)