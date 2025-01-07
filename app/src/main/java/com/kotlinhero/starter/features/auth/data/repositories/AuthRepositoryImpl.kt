package com.kotlinhero.starter.features.auth.data.repositories

import com.kotlinhero.starter.core.data.remote.repositories.BaseRepository
import com.kotlinhero.starter.core.utils.Either
import com.kotlinhero.starter.core.utils.failures.Failure
import com.kotlinhero.starter.features.auth.data.remote.api.AuthApi
import com.kotlinhero.starter.features.auth.data.remote.models.CredentialsDto
import com.kotlinhero.starter.features.auth.domain.repositories.AuthRepository
import org.koin.core.annotation.Factory

@Factory(binds = [AuthRepository::class])
class AuthRepositoryImpl(
    private val authApi: AuthApi
) : BaseRepository(), AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): Either<Failure, Unit> = safeApiCall {
        val credentialsDto = CredentialsDto(email = email, password = password)
        authApi.login(credentialsDto)
    }
}