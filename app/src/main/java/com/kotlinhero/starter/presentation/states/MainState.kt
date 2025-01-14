package com.kotlinhero.starter.presentation.states

import cafe.adriel.voyager.core.screen.Screen
import com.kotlinhero.starter.core.foundation.utils.states.ResultState

data class MainState(
    val startDestinationResultState: ResultState<Screen> = ResultState.Initial(),
)
