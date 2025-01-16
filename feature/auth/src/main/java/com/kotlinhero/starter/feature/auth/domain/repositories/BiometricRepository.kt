package com.kotlinhero.starter.feature.auth.domain.repositories

import androidx.appcompat.app.AppCompatActivity
import com.kotlinhero.starter.core.utils.Either
import com.kotlinhero.starter.core.utils.failures.Failure

interface BiometricRepository {
    suspend fun isBiometricLoginSetup(): Boolean

    suspend fun setupBiometricLogin(
        activity: AppCompatActivity,
        loginCredentials: com.kotlinhero.starter.feature.auth.domain.entities.LoginCredentials,
    ): Either<Failure, Unit>

    suspend fun biometricLogin(activity: AppCompatActivity): Either<Failure, Unit>
}