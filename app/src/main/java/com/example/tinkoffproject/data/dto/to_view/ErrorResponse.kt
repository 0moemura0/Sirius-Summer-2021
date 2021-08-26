package com.example.tinkoffproject.data.dto.to_view

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val status: Int?,
    val message: String?,
    val stackTrace: String?,
    val errors: List<ValidationError?>?
)