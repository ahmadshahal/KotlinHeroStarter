package com.kotlinhero.starter.core.auth.data.mappers

import com.kotlinhero.starter.core.auth.data.local.models.UserPreferences
import com.kotlinhero.starter.core.auth.data.remote.models.LoginCredentialsDto
import com.kotlinhero.starter.core.auth.data.remote.models.RegisterCredentialsDto
import com.kotlinhero.starter.core.auth.data.remote.models.UserDto
import com.kotlinhero.starter.core.auth.domain.entities.LoginCredentials
import com.kotlinhero.starter.core.auth.domain.entities.RegisterCredentials

fun LoginCredentials.toLoginCredentialsDto() = LoginCredentialsDto(
    email = email,
    password = password,
)

fun RegisterCredentials.toRegisterCredentialsDto() = RegisterCredentialsDto(
    email = email,
    password = password,
    fullName = fullName,
)

fun UserDto.toUserPreferences() = UserPreferences(
    email = email,
    fullName = fullName,
)
