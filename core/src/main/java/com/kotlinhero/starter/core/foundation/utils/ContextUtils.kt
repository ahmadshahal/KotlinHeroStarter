package com.kotlinhero.starter.core.foundation.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.biometric.BiometricManager
import com.kotlinhero.starter.core.biometrics.utils.isBiometricAvailable

fun Context.getActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}

fun Context.isBiometricAvailable() = BiometricManager.from(this).isBiometricAvailable()