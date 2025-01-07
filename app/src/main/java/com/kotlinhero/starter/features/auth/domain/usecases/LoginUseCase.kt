package com.kotlinhero.starter.features.auth.domain.usecases

import com.kotlinhero.starter.features.auth.domain.repositories.AuthRepository
import org.koin.core.annotation.Factory

@Factory
class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String) =
        authRepository.login(email = email, password = password)
}