package com.kotlinhero.starter.core.auth.data.local.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    @SerialName("email")
    val email: String = "",
)