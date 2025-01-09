package com.kotlinhero.starter.core.auth.domain.entities

data class RegisterCredentials(
    val email: String,
    val fullName: String,
    val password: String,
)