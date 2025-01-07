package com.kotlinhero.starter.features.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlinhero.starter.core.utils.states.FetchState
import com.kotlinhero.starter.features.auth.domain.usecases.LoginUseCase
import com.kotlinhero.starter.features.auth.presentation.states.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val mutableState = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = mutableState

    fun onEmailChange(email: String) =
        mutableState.update { it.copy(email = email) }

    fun onPasswordChange(password: String) =
        mutableState.update { it.copy(password = password) }

    fun login() {
        viewModelScope.launch {
            mutableState.update { it.copy(loginFetchState = FetchState.Loading()) }

            val email = mutableState.value.email
            val password = mutableState.value.password

            val result = loginUseCase(
                email = email,
                password = password
            )
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
}