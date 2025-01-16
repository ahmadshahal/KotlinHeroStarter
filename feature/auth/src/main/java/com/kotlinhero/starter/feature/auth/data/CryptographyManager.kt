package com.kotlinhero.starter.feature.auth.data

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import org.koin.core.annotation.Factory
import java.nio.charset.Charset
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

/**
 * Handles encryption and decryption using the Android Keystore system.
 */
interface CryptographyManager {

    /**
     * Initializes a Cipher in ENCRYPT_MODE for encrypting data.
     *
     * @param keyName The name of the key stored in the Android Keystore.
     * @return A configured Cipher ready for encryption.
     */
    fun getInitializedCipherForEncryption(keyName: String): Cipher

    /**
     * Initializes a Cipher in DECRYPT_MODE for decrypting data.
     *
     * @param keyName The name of the key stored in the Android Keystore.
     * @param initializationVector The initialization vector (IV) used during encryption.
     * @return A configured Cipher ready for decryption.
     */
    fun getInitializedCipherForDecryption(keyName: String, initializationVector: ByteArray): Cipher

    /**
     * Encrypts plaintext using the provided Cipher.
     *
     * @param plaintext The string to encrypt.
     * @param cipher The Cipher initialized for encryption.
     * @return A CiphertextWrapper containing the encrypted data and the IV.
     */
    fun encryptData(plaintext: String, cipher: Cipher): com.kotlinhero.starter.feature.auth.domain.entities.CipherText

    /**
     * Decrypts ciphertext using the provided Cipher.
     *
     * @param ciphertext The encrypted data to decrypt.
     * @param cipher The Cipher initialized for decryption.
     * @return The decrypted string.
     */
    fun decryptData(ciphertext: ByteArray, cipher: Cipher): String
}

/**
 * Implementation of the CryptographyManager interface.
 * Handles the actual encryption/decryption logic using Android Keystore.
 */
@Factory(binds = [CryptographyManager::class])
internal class CryptographyManagerImpl : CryptographyManager {

    private val keySize = 256 // AES key size
    private val androidKeyStore = "AndroidKeyStore" // Keystore type
    private val encryptionBlockMode = KeyProperties.BLOCK_MODE_GCM // Block mode
    private val encryptionPadding = KeyProperties.ENCRYPTION_PADDING_NONE // No padding
    private val encryptionAlgorithm = KeyProperties.KEY_ALGORITHM_AES // AES encryption

    /**
     * Configures and initializes a Cipher for encryption.
     */
    override fun getInitializedCipherForEncryption(keyName: String): Cipher {
        val cipher = getCipher()
        val secretKey = getOrCreateSecretKey(keyName)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        return cipher
    }

    /**
     * Configures and initializes a Cipher for decryption using a specific IV.
     */
    override fun getInitializedCipherForDecryption(
        keyName: String,
        initializationVector: ByteArray
    ): Cipher {
        val cipher = getCipher()
        val secretKey = getOrCreateSecretKey(keyName)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, GCMParameterSpec(128, initializationVector))
        return cipher
    }

    /**
     * Encrypts the given plaintext and returns a CiphertextWrapper containing the encrypted data and IV.
     */
    override fun encryptData(plaintext: String, cipher: Cipher): com.kotlinhero.starter.feature.auth.domain.entities.CipherText {
        val ciphertext = cipher.doFinal(plaintext.toByteArray(Charset.forName("UTF-8")))
        return com.kotlinhero.starter.feature.auth.domain.entities.CipherText(ciphertext, cipher.iv)
    }

    /**
     * Decrypts the given ciphertext using the provided Cipher and returns the plaintext string.
     */
    override fun decryptData(ciphertext: ByteArray, cipher: Cipher): String {
        val plaintext = cipher.doFinal(ciphertext)
        return String(plaintext, Charset.forName("UTF-8"))
    }

    /**
     * Retrieves a Cipher instance configured with the appropriate algorithm, block mode, and padding.
     */
    private fun getCipher(): Cipher {
        val transformation = "$encryptionAlgorithm/$encryptionBlockMode/$encryptionPadding"
        return Cipher.getInstance(transformation)
    }

    /**
     * Fetches an existing secret key from the Keystore or creates a new one if it does not exist.
     */
    private fun getOrCreateSecretKey(keyName: String): SecretKey {
        val keyStore = KeyStore.getInstance(androidKeyStore)
        keyStore.load(null) // Load the Keystore

        // If a key already exists, return it
        keyStore.getKey(keyName, null)?.let { return it as SecretKey }

        // Create a new key if none exists
        val paramsBuilder = KeyGenParameterSpec.Builder(
            keyName,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).apply {
            setBlockModes(encryptionBlockMode)
            setEncryptionPaddings(encryptionPadding)
            setKeySize(keySize)
            setUserAuthenticationRequired(true)
        }

        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            androidKeyStore
        )
        keyGenerator.init(paramsBuilder.build())
        return keyGenerator.generateKey()
    }
}