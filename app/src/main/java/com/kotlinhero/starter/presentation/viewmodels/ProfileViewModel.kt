package com.kotlinhero.starter.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlinhero.starter.core.utils.states.ResultState
import com.kotlinhero.starter.feature.auth.domain.usecases.GetUserFlowUseCase
import com.kotlinhero.starter.feature.auth.domain.usecases.LogoutUseCase
import com.kotlinhero.starter.presentation.states.ProfileState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ProfileViewModel(
    private val getUserFlowUseCase: GetUserFlowUseCase,
    private val logoutUseCase: LogoutUseCase,
) : ViewModel() {

    private val mutableState = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = mutableState

    init {
        observeUserFlow()
    }

    fun logout() {
        viewModelScope.launch {
            mutableState.update { it.copy(logoutResultState = ResultState.Loading()) }
            val result = logoutUseCase.invoke()
            result.fold(
                onLeft = { failure ->
                    mutableState.update { it.copy(logoutResultState = ResultState.Error(failure)) }
                },
                onRight = {
                    mutableState.update { it.copy(logoutResultState = ResultState.Success(Unit)) }
                }
            )
        }
    }

    fun resetLogoutResultState() =
        mutableState.update { it.copy(logoutResultState = ResultState.Initial()) }

    private fun observeUserFlow() {
        getUserFlowUseCase.invoke()
            .onEach { user -> mutableState.update { it.copy(user = user) } }
            .launchIn(viewModelScope)
    }
}