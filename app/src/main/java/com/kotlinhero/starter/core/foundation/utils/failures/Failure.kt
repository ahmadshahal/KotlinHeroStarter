package com.kotlinhero.starter.core.foundation.utils.failures

sealed class Failure {
    // Network failures
    data object NoInternetConnection : Failure()
    data object Timeout : Failure()
    data object ConnectionFailure : Failure()
    data object ConnectionLost : Failure()

    // HTTP failures
    data class ClientError(val statusCode: Int, val message: String) : Failure()
    data class InternalServerError(val statusCode: Int, val message: String) : Failure()
    data class RedirectError(val statusCode: Int, val message: String) : Failure()

    // Unknown or generic failures
    data class UnknownFailure(val message: String) : Failure()

    /**
     * Provides a user-friendly message for the failure.
     */
    fun getHumanReadableMessage(): String {
        return when (this) {
            NoInternetConnection -> "No internet connection. Please check your network and try again."
            Timeout -> "The request timed out. Please try again later."
            ConnectionFailure -> "Failed to establish a connection. Please check your network settings."
            ConnectionLost -> "The connection was lost unexpectedly. Please retry."
            is ClientError -> message
            is InternalServerError -> message
            is RedirectError -> "Redirection error: $message (Code: $statusCode)"
            is UnknownFailure -> "An unknown error occurred: $message"
        }
    }
}