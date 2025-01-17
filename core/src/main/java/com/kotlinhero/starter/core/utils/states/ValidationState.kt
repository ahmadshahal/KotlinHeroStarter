package com.kotlinhero.starter.core.utils.states


sealed class ValidationState {
    data object Valid : ValidationState()

    data class Invalid(val message: String) : ValidationState()

    data object Initial : ValidationState()

    val isInvalid get() = this is Invalid

    val isValid get() = this is Valid

    val isInitial get() = this is Initial

    val validateOnTheFly get() = !isInitial

    val errorMessageOrNull
        get() = when (this is Invalid) {
            true -> this.message
            false -> null
        }
}
