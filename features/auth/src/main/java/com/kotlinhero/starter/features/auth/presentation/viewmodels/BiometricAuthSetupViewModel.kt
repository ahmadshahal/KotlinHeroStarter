package com.kotlinhero.starter.features.auth.presentation.viewmodels

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlinhero.starter.core.domain.usecases.ValidateEmailUseCase
import com.kotlinhero.starter.core.utils.states.ResultState
import com.kotlinhero.starter.core.utils.states.ValidationState
import com.kotlinhero.starter.features.auth.domain.usecases.SetupBiometricAuthUseCase
import com.kotlinhero.starter.features.auth.presentation.states.BiometricAuthSetupState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class BiometricAuthSetupViewModel(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val setupBiometricAuthUseCase: SetupBiometricAuthUseCase,
) : ViewModel() {

    private val mutableState = MutableStateFlow(BiometricAuthSetupState())
    val state: StateFlow<BiometricAuthSetupState> = mutableState

    fun onEmailChange(email: String) {
        mutableState.update { it.copy(email = email) }
        if (mutableState.value.emailValidationState.validateOnTheFly) {
            validateEmail()
        }
    }

    fun onPasswordChange(password: String) =
        mutableState.update { it.copy(password = password) }

    fun authorize(activity: AppCompatActivity?) {
        if(activity == null) return

        validateEmail()
        if (mutableState.value.isInvalid()) return

        viewModelScope.launch {
            mutableState.update { it.copy(setupResultState = ResultState.Loading()) }

            val loginCredentials =
                com.kotlinhero.starter.features.auth.domain.entities.LoginCredentials(
                    email = mutableState.value.email,
                    password = mutableState.value.password,
                )
            val result = setupBiometricAuthUseCase(
                loginCredentials = loginCredentials,
                activity = activity,
            )

            result.fold(
                onLeft = { failure ->
                    mutableState.update {
                        it.copy(setupResultState = ResultState.Error(failure))
                    }
                },
                onRight = {
                    mutableState.update {
                        it.copy(setupResultState = ResultState.Success(Unit))
                    }
                },
            )
        }
    }

    fun resetSetupResultState() =
        mutableState.update { it.copy(setupResultState = ResultState.Initial()) }

    private fun validateEmail() {
        val result = validateEmailUseCase(mutableState.value.email)
        val validationState = result?.let { ValidationState.Invalid(it) } ?: ValidationState.Valid
        mutableState.update { it.copy(emailValidationState = validationState) }
    }
}