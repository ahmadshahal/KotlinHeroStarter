package com.kotlinhero.starter.feature.auth.presentation.states

import com.kotlinhero.starter.core.foundation.utils.states.ResultState
import com.kotlinhero.starter.core.foundation.utils.states.ValidationState

data class RegisterState(
    val registerResultState: ResultState<Unit> = ResultState.Initial(),
    val email: String = "",
    val fullName: String = "",
    val password: String = "",
    val emailValidationState: ValidationState = ValidationState.Initial,
    val fullNameValidationState: ValidationState = ValidationState.Initial,
    val passwordValidationState: ValidationState = ValidationState.Initial,
) {
    fun isInvalid() = listOf(
        emailValidationState,
        fullNameValidationState,
        passwordValidationState,
    ).any { it.isInvalid }
}
