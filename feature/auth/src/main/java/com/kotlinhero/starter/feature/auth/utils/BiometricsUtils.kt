package com.kotlinhero.starter.feature.auth.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.kotlinhero.starter.res.R
import timber.log.Timber

object BiometricPromptUtils {

    // Tag used for logging purposes, identifies the source of log messages.
    private const val TAG = "BiometricPromptUtils"

    /**
     * Creates a [BiometricPrompt] instance, used to display a biometric authentication dialog.
     *
     * @param activity The [AppCompatActivity] context required to attach the BiometricPrompt to the UI.
     * @param onSuccess A callback function invoked upon successful authentication, receiving the [BiometricPrompt.AuthenticationResult].
     * @return A configured [BiometricPrompt] instance.
     */
    fun createBiometricPrompt(
        activity: AppCompatActivity,
        onSuccess: (BiometricPrompt.AuthenticationResult) -> Unit,
        onFailure: () -> Unit = {},
    ): BiometricPrompt {
        // Executor to run callback operations on the main thread (UI thread).
        val executor = ContextCompat.getMainExecutor(activity)

        // AuthenticationCallback handles biometric events such as success, error, or failure.
        val callback = object : BiometricPrompt.AuthenticationCallback() {

            /**
             * Called when an unrecoverable error occurs during authentication.
             *
             * @param errCode An integer representing the type of error.
             * @param errString A description of the error for display purposes.
             */
            override fun onAuthenticationError(errCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errCode, errString)
                Timber.tag(TAG).d("errCode is $errCode and errString is: $errString")
                onFailure()
            }

            /**
             * Called when the biometric is valid but not recognized (e.g., fingerprint mismatch).
             * This indicates the authentication attempt failed.
             */
            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Timber.tag(TAG).d("User biometric rejected.")
                onFailure()
            }

            /**
             * Called when the authentication is successful, and a valid biometric is recognized.
             *
             * @param result The result of the successful authentication, containing crypto or user-related data.
             */
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Timber.tag(TAG).d("Authentication was successful")
                onSuccess(result) // Pass the successful result to the provided callback.
            }
        }

        // Return a BiometricPrompt instance, configured with the activity, executor, and callback.
        return BiometricPrompt(activity, executor, callback)
    }

    /**
     * Creates and returns a [BiometricPrompt.PromptInfo] object, which defines the UI and behavior of the biometric prompt.
     *
     * @return A configured [BiometricPrompt.PromptInfo] instance.
     */
    fun createPromptInfo(context: Context): BiometricPrompt.PromptInfo =
        BiometricPrompt.PromptInfo.Builder().apply {
            // Title displayed at the top of the biometric dialog.
            setTitle(context.getString(R.string.kotlin_hero_starter_app_authentication))

            // Subtitle displayed below the title in smaller font.
            setSubtitle(context.getString(R.string.please_login_to_get_access))

            // Determines if a confirmation step is required after successful authentication.
            setConfirmationRequired(false)

            // The text of the button displayed as an alternative to biometric authentication.
            setNegativeButtonText(context.getString(R.string.use_app_password))
        }.build()
}