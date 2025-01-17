package com.kotlinhero.starter.features.auth.presentation.states

import com.kotlinhero.starter.core.utils.states.ResultState
import com.kotlinhero.starter.core.utils.states.ValidationState

data class BiometricAuthSetupState(
    val setupResultState: ResultState<Unit> = ResultState.Initial(),
    val email: String = "",
    val password: String = "",
    val emailValidationState: ValidationState = ValidationState.Initial
) {
    fun isInvalid() = emailValidationState.isInvalid
}
