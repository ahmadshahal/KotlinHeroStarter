package com.kotlinhero.starter.features.auth.presentation.viewmodels

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlinhero.starter.core.auth.domain.entities.LoginCredentials
import com.kotlinhero.starter.core.auth.domain.usecases.LoginUseCase
import com.kotlinhero.starter.core.biometrics.domain.usecases.BiometricLoginUseCase
import com.kotlinhero.starter.core.biometrics.domain.usecases.IsBiometricLoginSetupUseCase
import com.kotlinhero.starter.core.foundation.domain.usecases.ValidateEmailUseCase
import com.kotlinhero.starter.core.foundation.utils.isBiometricAvailable
import com.kotlinhero.starter.core.foundation.utils.states.ResultState
import com.kotlinhero.starter.core.foundation.utils.states.ValidationState
import com.kotlinhero.starter.features.auth.presentation.states.BiometricLoginResultState
import com.kotlinhero.starter.features.auth.presentation.states.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class LoginViewModel(
    applicationContext: Context,
    private val loginUseCase: LoginUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val biometricLoginUseCase: BiometricLoginUseCase,
    private val isBiometricLoginSetupUseCase: IsBiometricLoginSetupUseCase,
) : ViewModel() {

    private val mutableState = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = mutableState

    init {
        val isBiometricAvailable = applicationContext.isBiometricAvailable()
        mutableState.update { it.copy(isBiometricAvailable = isBiometricAvailable) }
    }

    fun login() {
        validateEmail()
        if (mutableState.value.isInvalid()) return

        viewModelScope.launch {
            mutableState.update { it.copy(loginResultState = ResultState.Loading()) }

            val loginCredentials = LoginCredentials(
                email = mutableState.value.email,
                password = mutableState.value.password,
            )
            val result = loginUseCase(loginCredentials = loginCredentials)

            result.fold(
                onLeft = { failure ->
                    mutableState.update {
                        it.copy(loginResultState = ResultState.Error(failure))
                    }
                },
                onRight = {
                    mutableState.update {
                        it.copy(loginResultState = ResultState.Success(Unit))
                    }
                },
            )
        }
    }

    fun biometricLogin(activity: AppCompatActivity?) {
        if (activity == null) return

        viewModelScope.launch {
            val isBiometricLoginSetup = isBiometricLoginSetupUseCase()
            if (!isBiometricLoginSetup) {
                mutableState.update {
                    it.copy(
                        biometricResultState = BiometricLoginResultState.RequiresSetup
                    )
                }
                return@launch
            }

            val result = biometricLoginUseCase(activity = activity)
            result.fold(
                onLeft = { },
                onRight = {
                    mutableState.update {
                        it.copy(biometricResultState = BiometricLoginResultState.Success)
                    }
                }
            )
        }
    }

    fun onEmailChange(email: String) {
        mutableState.update { it.copy(email = email) }
        if (mutableState.value.emailValidationState.validateOnTheFly) {
            validateEmail()
        }
    }

    fun onPasswordChange(password: String) =
        mutableState.update { it.copy(password = password) }

    fun resetLoginResultState() = mutableState.update {
        it.copy(loginResultState = ResultState.Initial())
    }

    fun resetBiometricLoginResultState() = mutableState.update {
        it.copy(biometricResultState = BiometricLoginResultState.Initial)
    }

    private fun validateEmail() {
        val result = validateEmailUseCase(mutableState.value.email)
        val validationState = result?.let { ValidationState.Invalid(it) } ?: ValidationState.Valid
        mutableState.update { it.copy(emailValidationState = validationState) }
    }
}