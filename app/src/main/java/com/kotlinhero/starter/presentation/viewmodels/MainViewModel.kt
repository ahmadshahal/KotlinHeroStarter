package com.kotlinhero.starter.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlinhero.starter.features.auth.domain.usecases.IsAuthenticatedUseCase
import com.kotlinhero.starter.presentation.states.MainState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MainViewModel(
    private val isAuthenticatedUseCase: IsAuthenticatedUseCase,
) : ViewModel() {
    var showSplashScreen = true

    private val mutableState = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = mutableState

    init {
        setIsAuthenticated()
    }

    fun resetAuthenticationState() = mutableState.update {
        it.copy(isAuthenticated = false)
    }

    private fun setIsAuthenticated() {
        viewModelScope.launch {
            val isAuthenticated = isAuthenticatedUseCase()
            mutableState.update { it.copy(isAuthenticated = isAuthenticated) }
            delay(800)
            showSplashScreen = false
        }
    }
}