package com.kotlinhero.starter.core.biometrics.domain.usecases

import androidx.appcompat.app.AppCompatActivity
import com.kotlinhero.starter.core.auth.domain.entities.LoginCredentials
import com.kotlinhero.starter.core.biometrics.domain.repositories.BiometricRepository
import com.kotlinhero.starter.core.foundation.utils.Either
import com.kotlinhero.starter.core.foundation.utils.failures.Failure
import org.koin.core.annotation.Factory

@Factory
class SetupBiometricLoginUseCase(private val biometricsRepository: BiometricRepository) {
    suspend operator fun invoke(
        activity: AppCompatActivity,
        loginCredentials: LoginCredentials
    ): Either<Failure, Unit> {
        return biometricsRepository.setupBiometricLogin(
            activity = activity,
            loginCredentials = loginCredentials
        )
    }
}