package com.kotlinhero.starter.app.presentation.states

import com.kotlinhero.starter.core.auth.domain.entities.User
import com.kotlinhero.starter.core.foundation.utils.states.ResultState

data class ProfileState(
    val user: User = User(),
    val logoutResultState: ResultState<Unit> = ResultState.Initial(),
)
