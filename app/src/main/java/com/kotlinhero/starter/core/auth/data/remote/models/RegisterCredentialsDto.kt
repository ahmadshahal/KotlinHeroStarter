package com.kotlinhero.starter.core.auth.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterCredentialsDto(
    @SerialName("email")
    val email: String,

    @SerialName("password")
    val password: String,
)