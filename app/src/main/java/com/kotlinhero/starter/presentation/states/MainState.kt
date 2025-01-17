package com.kotlinhero.starter.presentation.states

import com.kotlinhero.starter.core.utils.states.ResultState
import com.kotlinhero.starter.domain.usecases.StartDestinationResult

data class MainState(
    val startDestinationResultState: ResultState<StartDestinationResult> = ResultState.Initial(),
)
