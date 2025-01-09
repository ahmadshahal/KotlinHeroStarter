package com.kotlinhero.starter.core.foundation.domain.usecases

import org.koin.core.annotation.Factory

@Factory
class ValidatePasswordUseCase {
    operator fun invoke(password: String): String? {
        if(password.isBlank()) {
            return "Your password can't be empty"
        }
        return null
    }
}