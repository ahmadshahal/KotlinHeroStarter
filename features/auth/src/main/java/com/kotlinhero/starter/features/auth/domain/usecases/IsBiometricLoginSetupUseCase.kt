package com.kotlinhero.starter.features.auth.domain.usecases

import com.kotlinhero.starter.features.auth.domain.repositories.BiometricRepository
import org.koin.core.annotation.Factory

@Factory
class IsBiometricLoginSetupUseCase(private val biometricRepository: BiometricRepository) {
    suspend operator fun invoke(): Boolean {
        return biometricRepository.isBiometricLoginSetup()
    }
}