package com.kotlinhero.starter.feature.auth.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginCredentialsDto(
    @SerialName("email")
    val email: String,

    @SerialName("password")
    val password: String,
)