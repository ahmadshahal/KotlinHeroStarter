package com.kotlinhero.starter.core.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NormalResponseBody<T>(
    @SerialName("data")
    val data: T
)
