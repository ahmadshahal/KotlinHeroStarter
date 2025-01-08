package com.kotlinhero.starter.core.foundation.utils

sealed class Either<out L, out R> {
    data class Left<out L>(val value: L) : Either<L, Nothing>()
    data class Right<out R>(val value: R) : Either<Nothing, R>()

    /**
     * Maps a Right value using the given transform function, leaving a Left value unchanged.
     */
    fun <T> map(transform: (R) -> T): Either<L, T> = when (this) {
        is Left -> this
        is Right -> Right(transform(value))
    }

    /**
     * Maps a Left value using the given transform function, leaving a Right value unchanged.
     */
    fun <T> mapLeft(transform: (L) -> T): Either<T, R> = when (this) {
        is Left -> Left(transform(value))
        is Right -> this
    }

    /**
     * Returns the result of applying either the left or right function depending on the type of value.
     */
    fun <T> fold(onLeft: (L) -> T, onRight: (R) -> T): T = when (this) {
        is Left -> onLeft(value)
        is Right -> onRight(value)
    }

    /**
     * Checks if the instance is a Left value.
     */
    fun isLeft(): Boolean = this is Left

    /**
     * Checks if the instance is a Right value.
     */
    fun isRight(): Boolean = this is Right
}