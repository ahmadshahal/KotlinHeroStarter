package com.kotlinhero.starter.features.auth.domain.usecases

import com.kotlinhero.starter.features.auth.data.BiometricAuthenticator
import org.koin.core.annotation.Factory

@Factory
class BiometricAuthSetupRequiredUseCase(
    private val biometricAuthenticator: BiometricAuthenticator
) {
    suspend operator fun invoke(): Boolean {
        return biometricAuthenticator.isSetupRequired()
    }
}