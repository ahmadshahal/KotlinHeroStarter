package com.kotlinhero.starter.features.auth.domain.entities

data class AuthorizationTokens(
    val accessToken: String,
    val refreshToken: String,
)
