package com.kotlinhero.starter.app.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlinhero.starter.app.domain.usecases.GetStartDestinationUseCase
import com.kotlinhero.starter.app.presentation.states.MainState
import com.kotlinhero.starter.core.foundation.utils.states.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MainViewModel(
    private val getStartDestinationUseCase: GetStartDestinationUseCase,
) : ViewModel() {

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
            val startDestination = getStartDestinationUseCase()
            mutableState.update {
                it.copy(startDestinationResultState = ResultState.Success(startDestination))
            }
        }
    }
}