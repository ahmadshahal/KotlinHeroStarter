package com.kotlinhero.starter.features.auth.presentation.states

import com.kotlinhero.starter.core.utils.states.FetchState

data class LoginState(
    val loginFetchState: FetchState<Unit> = FetchState.Initial(),
    val email: String = "",
    val password: String = "",
)
