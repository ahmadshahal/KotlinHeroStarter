package com.kotlinhero.starter.feature.auth.domain.entities

data class AuthorizationTokens(
    val accessToken: String,
    val refreshToken: String,
)
