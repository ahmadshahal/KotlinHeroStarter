package com.kotlinhero.starter.feature.auth.presentation.states

import com.kotlinhero.starter.core.foundation.utils.states.ResultState
import com.kotlinhero.starter.core.foundation.utils.states.ValidationState

data class LoginState(
    val loginResultState: ResultState<Unit> = ResultState.Initial(),
    val biometricResultState: BiometricLoginResultState = BiometricLoginResultState.Initial,
    val email: String = "",
    val password: String = "",
    val emailValidationState: ValidationState = ValidationState.Initial,
    val isBiometricAvailable: Boolean = false,
) {
    fun isInvalid() = emailValidationState.isInvalid
}

sealed class BiometricLoginResultState {
    data object Success : BiometricLoginResultState()

    data object Initial : BiometricLoginResultState()

    data object RequiresSetup : BiometricLoginResultState()

    val isSuccess get() = this is Success

    val isRequiresSetup get() = this is RequiresSetup
}
