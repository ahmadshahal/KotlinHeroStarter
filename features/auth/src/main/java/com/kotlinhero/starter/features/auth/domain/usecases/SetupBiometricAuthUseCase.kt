package com.kotlinhero.starter.features.auth.domain.usecases

import androidx.appcompat.app.AppCompatActivity
import com.kotlinhero.starter.core.utils.Either
import com.kotlinhero.starter.core.utils.failures.Failure
import com.kotlinhero.starter.features.auth.data.BiometricAuthenticator
import com.kotlinhero.starter.features.auth.domain.entities.LoginCredentials
import org.koin.core.annotation.Factory

@Factory
class SetupBiometricAuthUseCase(private val biometricsRepository: BiometricAuthenticator) {
    suspend operator fun invoke(
        activity: AppCompatActivity,
        loginCredentials: LoginCredentials
    ): Either<Failure, Unit> {
        return biometricsRepository.setupAuthentication(
            activity = activity,
            loginCredentials = loginCredentials
        )
    }
}