package com.example.tinkoffproject.data.dto.to_view

import kotlinx.serialization.Serializable

@Serializable
data class ValidationError(
    val field: String?,
    val message: String?
)