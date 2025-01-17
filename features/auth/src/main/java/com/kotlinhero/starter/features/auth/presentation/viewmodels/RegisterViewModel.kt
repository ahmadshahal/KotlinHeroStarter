package com.kotlinhero.starter.features.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlinhero.starter.core.domain.usecases.ValidateEmailUseCase
import com.kotlinhero.starter.core.domain.usecases.ValidateFullNameUseCase
import com.kotlinhero.starter.core.domain.usecases.ValidatePasswordUseCase
import com.kotlinhero.starter.core.utils.states.ResultState
import com.kotlinhero.starter.core.utils.states.ValidationState
import com.kotlinhero.starter.features.auth.domain.usecases.RegisterUseCase
import com.kotlinhero.starter.features.auth.presentation.states.RegisterState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class RegisterViewModel(
    private val registerUseCase: RegisterUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateFullNameUseCase: ValidateFullNameUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
) : ViewModel() {

    private val mutableState = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = mutableState

    fun onEmailChange(email: String) {
        mutableState.update { it.copy(email = email) }
        if (mutableState.value.emailValidationState.validateOnTheFly) {
            validateEmail()
        }
    }

    fun onFullNameChange(fullName: String) {
        mutableState.update { it.copy(fullName = fullName) }
        if (mutableState.value.fullNameValidationState.validateOnTheFly) {
            validateFullName()
        }
    }

    fun onPasswordChange(password: String) {
        mutableState.update { it.copy(password = password) }
        if (mutableState.value.passwordValidationState.validateOnTheFly) {
            validatePassword()
        }
    }

    fun resetRegisterResultState() = mutableState.update {
        it.copy(registerResultState = ResultState.Initial())
    }

    fun register() {
        validateEmail()
        validatePassword()
        validateFullName()
        if(mutableState.value.isInvalid()) return

        viewModelScope.launch {
            mutableState.update { it.copy(registerResultState = ResultState.Loading()) }

            val registerCredentials =
                com.kotlinhero.starter.features.auth.domain.entities.RegisterCredentials(
                    email = mutableState.value.email,
                    password = mutableState.value.password,
                    fullName = mutableState.value.fullName
                )
            val result = registerUseCase(registerCredentials = registerCredentials)

            result.fold(
                onLeft = { failure ->
                    mutableState.update {
                        it.copy(registerResultState = ResultState.Error(failure))
                    }
                },
                onRight = {
                    mutableState.update {
                        it.copy(registerResultState = ResultState.Success(Unit))
                    }
                },
            )
        }
    }

    private fun validateEmail() {
        val result = validateEmailUseCase(mutableState.value.email)
        val validationState = result?.let { ValidationState.Invalid(it) } ?: ValidationState.Valid
        mutableState.update { it.copy(emailValidationState = validationState) }
    }

    private fun validateFullName() {
        val result = validateFullNameUseCase(mutableState.value.fullName)
        val validationState = result?.let { ValidationState.Invalid(it) } ?: ValidationState.Valid
        mutableState.update { it.copy(fullNameValidationState = validationState) }
    }

    private fun validatePassword() {
        val result = validatePasswordUseCase(mutableState.value.password)
        val validationState = result?.let { ValidationState.Invalid(it) } ?: ValidationState.Valid
        mutableState.update { it.copy(passwordValidationState = validationState) }
    }
}