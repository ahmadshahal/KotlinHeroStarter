package com.kotlinhero.starter.core.auth.data.remote.api

import com.kotlinhero.starter.core.auth.data.remote.models.AuthorizationDetailsDto
import com.kotlinhero.starter.core.auth.data.remote.models.AuthorizationRefreshDto
import com.kotlinhero.starter.core.auth.data.remote.models.LoginCredentialsDto
import com.kotlinhero.starter.core.auth.data.remote.models.RegisterCredentialsDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.koin.core.annotation.Factory

@Factory
class AuthApi(private val httpClient: HttpClient) {

    suspend fun login(loginCredentialsDto: LoginCredentialsDto): AuthorizationDetailsDto {
        return httpClient.post("/v1/auth/login") {
            setBody(loginCredentialsDto)
        }.body()
    }

    suspend fun register(registerCredentialsDto: RegisterCredentialsDto): AuthorizationDetailsDto {
        return httpClient.post("/v1/auth/register") {
            setBody(registerCredentialsDto)
        }.body()
    }

    suspend fun refreshToken(refreshToken: String): AuthorizationRefreshDto {
        return httpClient.get("/v1/auth/refresh-token") {
            headers.append("Authorization", "Bearer $refreshToken")
        }.body()
    }

    suspend fun logout() = httpClient.get("/v1/auth/logout")
}