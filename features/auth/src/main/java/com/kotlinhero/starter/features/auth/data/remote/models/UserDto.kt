package com.kotlinhero.starter.features.auth.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("email")
    val email: String,
    @SerialName("fullName")
    val fullName: String,
)