package com.kotlinhero.starter.core.auth.domain.entities

data class AuthorizationTokens(
    val accessToken: String,
    val refreshToken: String,
)
