package com.example.tinkoffproject.model.data.network.dto

import kotlinx.serialization.*

@Serializable
data class ErrorResponse(
    val status: Int?,
    val message: String?,
    val stackTrace: String?,
    val errors: List<ValidationError?>?
)