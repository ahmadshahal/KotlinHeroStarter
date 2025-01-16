package com.kotlinhero.starter.feature.auth.domain.usecases

import androidx.appcompat.app.AppCompatActivity
import com.kotlinhero.starter.core.utils.Either
import com.kotlinhero.starter.core.utils.failures.Failure
import com.kotlinhero.starter.feature.auth.domain.entities.LoginCredentials
import com.kotlinhero.starter.feature.auth.domain.repositories.BiometricRepository
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