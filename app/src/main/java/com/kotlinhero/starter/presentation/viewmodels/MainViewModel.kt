package com.kotlinhero.starter.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlinhero.starter.core.foundation.utils.states.ResultState
import com.kotlinhero.starter.domain.usecases.GetStartDestinationUseCase
import com.kotlinhero.starter.presentation.states.MainState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MainViewModel(
    private val getStartDestinationUseCase: GetStartDestinationUseCase,
) : ViewModel() {
    var showSplashScreen = true

    private val mutableState = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = mutableState

    init {
        setStartDestination()
    }

    fun resetStartDestinationResultState() = mutableState.update {
        it.copy(startDestinationResultState = ResultState.Initial())
    }

    private fun setStartDestination() {
        viewModelScope.launch {
            val startDestinationResult = getStartDestinationUseCase()
            mutableState.update {
                it.copy(
                    startDestinationResultState = ResultState.Success(startDestinationResult)
                )
            }
            delay(800)
            showSplashScreen = false
        }
    }
}