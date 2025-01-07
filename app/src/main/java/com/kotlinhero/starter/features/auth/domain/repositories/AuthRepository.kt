package com.kotlinhero.starter.features.auth.domain.repositories

import com.kotlinhero.starter.core.utils.Either
import com.kotlinhero.starter.core.utils.failures.Failure

interface AuthRepository {
    suspend fun login(email: String, password: String): Either<Failure, Unit>
}