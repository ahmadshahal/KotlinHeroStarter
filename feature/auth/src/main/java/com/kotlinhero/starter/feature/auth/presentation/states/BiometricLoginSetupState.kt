package com.kotlinhero.starter.feature.auth.presentation.states

import com.kotlinhero.starter.core.foundation.utils.states.ResultState
import com.kotlinhero.starter.core.foundation.utils.states.ValidationState

data class BiometricLoginSetupState(
    val loginResultState: ResultState<Unit> = ResultState.Initial(),
    val email: String = "",
    val password: String = "",
    val emailValidationState: ValidationState = ValidationState.Initial
) {
    fun isInvalid() = emailValidationState.isInvalid
}
