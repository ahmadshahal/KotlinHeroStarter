package com.kotlinhero.starter.features.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlinhero.starter.core.auth.domain.entities.LoginCredentials
import com.kotlinhero.starter.core.foundation.domain.usecases.ValidateEmailUseCase
import com.kotlinhero.starter.core.foundation.utils.states.FetchState
import com.kotlinhero.starter.core.foundation.utils.states.ValidationState
import com.kotlinhero.starter.features.auth.domain.usecases.LoginUseCase
import com.kotlinhero.starter.features.auth.presentation.states.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
) : ViewModel() {
    private val mutableState = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = mutableState

    fun onEmailChange(email: String) {
        mutableState.update { it.copy(email = email) }
        if (mutableState.value.emailValidationState.validateOnTheFly) {
            validateEmail()
        }
    }

    fun onPasswordChange(password: String) =
        mutableState.update { it.copy(password = password) }

    fun resetFetchState() = mutableState.update {
        it.copy(loginFetchState = FetchState.Initial())
    }

    fun login() {
        validateEmail()
        if(mutableState.value.isInvalid()) {
            return
        }
        viewModelScope.launch {
            mutableState.update { it.copy(loginFetchState = FetchState.Loading()) }

            val loginCredentials = LoginCredentials(
                email = mutableState.value.email,
                password = mutableState.value.password,
            )
            val result = loginUseCase(loginCredentials = loginCredentials)

            result.fold(
                onLeft = { failure ->
                    mutableState.update {
                        it.copy(loginFetchState = FetchState.Error(failure))
                    }
                },
                onRight = {
                    mutableState.update {
                        it.copy(loginFetchState = FetchState.Success(Unit))
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
}