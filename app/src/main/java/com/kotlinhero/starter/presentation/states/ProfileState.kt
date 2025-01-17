package com.kotlinhero.starter.presentation.states

import com.kotlinhero.starter.core.utils.states.ResultState
import com.kotlinhero.starter.features.auth.domain.entities.User

data class ProfileState(
    val user: User = User(),
    val logoutResultState: ResultState<Unit> = ResultState.Initial(),
)
