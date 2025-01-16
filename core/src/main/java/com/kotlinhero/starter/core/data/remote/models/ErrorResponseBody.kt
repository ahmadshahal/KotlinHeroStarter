package com.kotlinhero.starter.core.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponseBody(
    @SerialName("details")
    val details: List<String>,
    @SerialName("message")
    val message: String,
    @SerialName("status")
    val status: Int
)
