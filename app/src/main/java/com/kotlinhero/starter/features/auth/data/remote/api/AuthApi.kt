package com.kotlinhero.starter.features.auth.data.remote.api

import com.kotlinhero.starter.core.data.remote.models.NormalResponseBody
import com.kotlinhero.starter.features.auth.data.remote.models.AccessDetailsDto
import com.kotlinhero.starter.features.auth.data.remote.models.CredentialsDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.koin.core.annotation.Factory

@Factory
class AuthApi(private val httpClient: HttpClient) {
    suspend fun login(credentialsDto: CredentialsDto): AccessDetailsDto {
        val response = httpClient.post("/v1/auth/login") {
            setBody(credentialsDto)
        }
        return response.body<NormalResponseBody<AccessDetailsDto>>().data
    }
}