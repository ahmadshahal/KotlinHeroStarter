package com.kotlinhero.starter.core.foundation.domain.usecases

import android.util.Patterns
import org.koin.core.annotation.Factory

@Factory
class ValidateEmailUseCase {
    operator fun invoke(email: String): String? {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return "Invalid email"
        }
        return null
    }
}