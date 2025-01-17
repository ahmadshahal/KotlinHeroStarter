package com.kotlinhero.starter.core.domain.usecases

import org.koin.core.annotation.Factory

@Factory
class ValidateFullNameUseCase {
    operator fun invoke(fullName: String): String? {
        if(fullName.trim().isBlank()) {
            return "Your name can't be empty"
        }
        return null
    }
}