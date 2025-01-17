package com.kotlinhero.starter.features.auth.data.local.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    @SerialName("email")
    val email: String = "",
    @SerialName("fullName")
    val fullName: String = "",
)