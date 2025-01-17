package com.kotlinhero.starter.features.auth.domain.repositories

import androidx.appcompat.app.AppCompatActivity
import com.kotlinhero.starter.core.utils.Either
import com.kotlinhero.starter.core.utils.failures.Failure
import com.kotlinhero.starter.features.auth.domain.entities.LoginCredentials

interface BiometricRepository {
    suspend fun isBiometricLoginSetup(): Boolean

    suspend fun setupBiometricLogin(
        activity: AppCompatActivity,
        loginCredentials: LoginCredentials,
    ): Either<Failure, Unit>

    suspend fun biometricLogin(activity: AppCompatActivity): Either<Failure, Unit>
}