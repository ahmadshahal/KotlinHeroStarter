package com.kotlinhero.starter.features.auth.domain.entities

data class LoginCredentials(
    val email: String,
    val password: String,
)