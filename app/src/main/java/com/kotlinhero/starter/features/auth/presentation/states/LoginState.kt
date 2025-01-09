package com.kotlinhero.starter.features.auth.presentation.states

import com.kotlinhero.starter.core.foundation.utils.states.FetchState
import com.kotlinhero.starter.core.foundation.utils.states.ValidationState

data class LoginState(
    val loginFetchState: FetchState<Unit> = FetchState.Initial(),
    val email: String = "",
    val password: String = "",
    val emailValidationState: ValidationState = ValidationState.Initial
) {
    fun isInvalid() = emailValidationState.isInvalid
}
