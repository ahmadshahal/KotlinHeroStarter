package com.kotlinhero.starter.feature.auth.domain.entities

data class RegisterCredentials(
    val email: String,
    val fullName: String,
    val password: String,
)