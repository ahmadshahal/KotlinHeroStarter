package com.kotlinhero.starter.features.auth.domain.usecases

import androidx.appcompat.app.AppCompatActivity
import com.kotlinhero.starter.core.utils.Either
import com.kotlinhero.starter.core.utils.failures.Failure
import com.kotlinhero.starter.features.auth.data.BiometricAuthenticator
import org.koin.core.annotation.Factory

@Factory
class BiometricAuthenticateUseCase(private val biometricAuthenticator: BiometricAuthenticator) {
    suspend operator fun invoke(activity: AppCompatActivity): Either<Failure, Unit> {
        return biometricAuthenticator.authenticate(activity = activity)
    }
}