package com.kotlinhero.starter.features.auth.domain.usecases

import androidx.appcompat.app.AppCompatActivity
import com.kotlinhero.starter.core.utils.Either
import com.kotlinhero.starter.core.utils.failures.Failure
import com.kotlinhero.starter.features.auth.domain.repositories.BiometricRepository
import org.koin.core.annotation.Factory

@Factory
class BiometricLoginUseCase(private val biometricRepository: BiometricRepository) {
    suspend operator fun invoke(activity: AppCompatActivity): Either<Failure, Unit> {
        return biometricRepository.biometricLogin(activity = activity)
    }
}