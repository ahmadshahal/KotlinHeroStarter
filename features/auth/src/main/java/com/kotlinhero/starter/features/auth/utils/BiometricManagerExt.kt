package com.kotlinhero.starter.features.auth.utils

import android.content.Context
import androidx.biometric.BiometricManager

fun BiometricManager.isBiometricAvailable(): Boolean {
    val authenticators = BiometricManager.Authenticators.DEVICE_CREDENTIAL or
            BiometricManager.Authenticators.BIOMETRIC_STRONG or
            BiometricManager.Authenticators.BIOMETRIC_WEAK
    return canAuthenticate(authenticators) == BiometricManager.BIOMETRIC_SUCCESS
}

fun Context.isBiometricAvailable() = BiometricManager.from(this).isBiometricAvailable()