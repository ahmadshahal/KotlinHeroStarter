package com.kotlinhero.starter.core.foundation.utils

sealed class Either<L, R> {
    data class Left<L, R>(val value: L) : Either<L, R>()

    data class Right<L, R>(val value: R) : Either<L, R>()

    fun <T> mapRight(transform: (R) -> T): Either<L, T> = when (this) {
        is Left -> Left(this.value)
        is Right -> Right(transform(this.value))
    }

    suspend fun <T> mapRightSuspend(transform: suspend (R) -> T): Either<L, T> = when (this) {
        is Left -> Left(this.value)
        is Right -> Right(transform(this.value))
    }

    fun <T> mapLeft(transform: (L) -> T): Either<T, R> = when (this) {
        is Left -> Left(transform(this.value))
        is Right -> Right(this.value)
    }

    fun <T> flatMapRight(transform: (R) -> Either<L, T>): Either<L, T> = when (this) {
        is Left -> Left(this.value)
        is Right -> transform(this.value)
    }

    suspend fun <T> flatMapRightSuspend(transform: suspend (R) -> Either<L, T>): Either<L, T> = when (this) {
        is Left -> Left(this.value)
        is Right -> transform(this.value)
    }

    fun <T> flatMapLeft(transform: (L) -> Either<T, R>): Either<T, R> = when (this) {
        is Left -> transform(this.value)
        is Right -> Right(this.value)
    }

    fun leftOrNull(): L? = when (this) {
        is Left -> this.value
        is Right -> null
    }

    fun rightOrNull(): R? = when (this) {
        is Left -> null
        is Right -> this.value
    }

    fun <T> fold(onLeft: (L) -> T, onRight: (R) -> T): T = when (this) {
        is Left -> onLeft(this.value)
        is Right -> onRight(this.value)
    }

    fun isLeft(): Boolean = this is Left

    fun isRight(): Boolean = this is Right
}