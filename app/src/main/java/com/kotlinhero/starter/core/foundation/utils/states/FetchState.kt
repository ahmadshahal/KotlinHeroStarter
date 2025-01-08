package com.kotlinhero.starter.core.foundation.utils.states

import com.kotlinhero.starter.core.foundation.utils.failures.Failure

sealed class FetchState<T> {
    data class Success<T>(val data: T) : FetchState<T>()

    data class Error<T>(val failure: Failure) : FetchState<T>()

    class Loading<T> : FetchState<T>()

    class Initial<T> : FetchState<T>()

    val isLoading get() = this is Loading

    val isError get() = this is Error

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
