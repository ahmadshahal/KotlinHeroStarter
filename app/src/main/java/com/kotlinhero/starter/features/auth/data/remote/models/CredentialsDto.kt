package com.kotlinhero.starter.features.auth.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CredentialsDto(
    @SerialName("email")
    val email: String,

    @SerialName("password")
    val password: String,
)