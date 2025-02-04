package com.kotlinhero.starter.features.auth.data

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import com.kotlinhero.starter.core.utils.Either
import com.kotlinhero.starter.core.utils.failures.Failure
import com.kotlinhero.starter.features.auth.domain.entities.AuthorizationTokens
import com.kotlinhero.starter.features.auth.domain.entities.LoginCredentials
import com.kotlinhero.starter.features.auth.utils.BiometricPromptUtils
import com.kotlinhero.starter.features.auth.utils.isBiometricAvailable
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import org.koin.core.annotation.Factory
import javax.crypto.Cipher
import kotlin.coroutines.cancellation.CancellationException

private const val SECRET_KEY_NAME = "biometric_secret_key"

interface BiometricAuthenticator {
    suspend fun isSetupRequired(): Boolean

    suspend fun setupAuthentication(
        activity: AppCompatActivity,
        loginCredentials: LoginCredentials,
    ): Either<Failure, Unit>

    suspend fun authenticate(activity: AppCompatActivity): Either<Failure, Unit>
}

@Factory(binds = [BiometricAuthenticator::class])
internal class BiometricAuthenticatorImpl(
    private val cryptographyManager: CryptographyManager,
    private val sessionManager: SessionManager,
    private val applicationContext: Context,
) : BiometricAuthenticator {

    override suspend fun isSetupRequired(): Boolean {
        val ciphertextWrapper = sessionManager.getSecretToken()
        return ciphertextWrapper != null
    }

    override suspend fun setupAuthentication(
        activity: AppCompatActivity,
        loginCredentials: LoginCredentials,
    ): Either<Failure, Unit> {
        // TODO: Replace with this in real world scenario..
        // val loginResult = sessionManager.login(loginCredentials)

        delay(3000)
        val tokens = AuthorizationTokens(accessToken = "1234", refreshToken = "12345")
        val loginResult = Either.Right<Failure, AuthorizationTokens>(tokens)

        return loginResult.flatMapRight { authorizationTokens ->
            val isBiometricAvailable = applicationContext.isBiometricAvailable()
            if (!isBiometricAvailable) {
                return Either.Left(Failure.BiometricsUnavailable)
            }

            val secretKeyName = SECRET_KEY_NAME
            val cipher = cryptographyManager.getInitializedCipherForEncryption(secretKeyName)

            val result = showBiometricPromptAndAwaitResults(
                cipher = cipher,
                activity = activity,
            )

            return result.mapRight { resultCipher ->
                // Encrypt the token and save it securely
                val encryptedToken = cryptographyManager.encryptData(
                    plaintext = authorizationTokens.refreshToken,
                    cipher = resultCipher
                )
                sessionManager.saveSecretToken(cipherText = encryptedToken)
            }
        }
    }

    override suspend fun authenticate(activity: AppCompatActivity): Either<Failure, Unit> {
        val isBiometricAvailable = applicationContext.isBiometricAvailable()
        if (!isBiometricAvailable) {
            return Either.Left(Failure.BiometricsUnavailable)
        }

        val ciphertextWrapper = sessionManager.getSecretToken()
            ?: return Either.Left(Failure.BiometricsNotInitialized)

        val secretKeyName = SECRET_KEY_NAME
        val cipher = cryptographyManager.getInitializedCipherForDecryption(
            keyName = secretKeyName,
            initializationVector = ciphertextWrapper.initializationVector
        )

        val result = showBiometricPromptAndAwaitResults(
            cipher = cipher,
            activity = activity,
        )

        return result.mapRight { resultCipher ->
            val decryptedToken = cryptographyManager.decryptData(
                ciphertext = ciphertextWrapper.ciphertext,
                cipher = resultCipher
            )
            sessionManager.login(decryptedToken)
        }
    }

    /**
     * Displays a biometric prompt and waits for the user's authentication result.
     *
     * @param cipher The initialized [Cipher] for biometric authentication.
     * @param activity The [AppCompatActivity] used to show the biometric prompt.
     * @return [Either] containing a [Failure] if authentication fails or the authenticated [Cipher] on success.
     */
    private suspend fun showBiometricPromptAndAwaitResults(
        cipher: Cipher,
        activity: AppCompatActivity
    ): Either<Failure, Cipher> {
        return try {
            val resultCipher = suspendCancellableCoroutine<Cipher> { continuation ->
                val biometricPrompt = BiometricPromptUtils.createBiometricPrompt(
                    activity = activity,
                    onSuccess = { result ->
                        result.cryptoObject?.cipher?.let {
                            continuation.resume(it) { cause, _, _ ->
                                continuation.cancel(cause)
                            }
                        } ?: continuation.cancel(CancellationException("No Cipher in CryptoObject"))
                    },
                    onFailure = {
                        continuation.cancel(CancellationException("Biometric authentication failed"))
                    }
                )

                val promptInfo = BiometricPromptUtils.createPromptInfo(activity)
                biometricPrompt.authenticate(promptInfo, BiometricPrompt.CryptoObject(cipher))
            }
            Either.Right(resultCipher)
        } catch (exception: Exception) {
            val failure = when (exception) {
                is CancellationException -> Failure.BiometricsAuthenticationFailed
                else -> Failure.UnknownFailure(
                    exception.message ?: "Unknown error during biometric authentication"
                )
            }
            Either.Left(failure)
        }
    }
}