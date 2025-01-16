package com.kotlinhero.starter.core.utils.states

import com.kotlinhero.starter.core.utils.failures.Failure

sealed class ResultState<T> {
    data class Success<T>(val data: T) : ResultState<T>()

    data class Error<T>(val failure: Failure) : ResultState<T>()

    class Loading<T> : ResultState<T>()

    class Initial<T> : ResultState<T>()

    val isLoading get() = this is Loading

    val isError get() = this is Error

    val isSuccess get() = this is Success

    val failureOrNull
        get() = when (this is Error) {
            true -> this.failure
            false -> null
        }

    val dataOrNull
        get() = when (this is Success) {
            true -> this.data
            false -> null
        }
}
