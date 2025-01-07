package com.kotlinhero.starter.core.utils.exceptions

class RemoteException(
    message: String,
    val code: Int
) : Exception(message)
