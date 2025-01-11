package com.kotlinhero.starter.core.biometrics.domain.repositories

import androidx.appcompat.app.AppCompatActivity
import com.kotlinhero.starter.core.auth.domain.entities.LoginCredentials
import com.kotlinhero.starter.core.foundation.utils.Either
import com.kotlinhero.starter.core.foundation.utils.failures.Failure

interface BiometricRepository {
    suspend fun isBiometricLoginSetup(): Boolean

    suspend fun setupBiometricLogin(
        activity: AppCompatActivity,
        loginCredentials: LoginCredentials,
    ): Either<Failure, Unit>

    suspend fun biometricLogin(activity: AppCompatActivity): Either<Failure, Unit>
}