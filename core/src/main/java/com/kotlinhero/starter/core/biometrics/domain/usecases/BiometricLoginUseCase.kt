package com.kotlinhero.starter.core.biometrics.domain.usecases

import androidx.appcompat.app.AppCompatActivity
import com.kotlinhero.starter.core.biometrics.domain.repositories.BiometricRepository
import com.kotlinhero.starter.core.foundation.utils.Either
import com.kotlinhero.starter.core.foundation.utils.failures.Failure
import org.koin.core.annotation.Factory

@Factory
class BiometricLoginUseCase(private val biometricRepository: BiometricRepository) {
    suspend operator fun invoke(activity: AppCompatActivity): Either<Failure, Unit> {
        return biometricRepository.biometricLogin(activity = activity)
    }
}