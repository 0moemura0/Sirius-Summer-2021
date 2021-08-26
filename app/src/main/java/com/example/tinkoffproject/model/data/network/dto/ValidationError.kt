package com.example.tinkoffproject.model.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class ValidationError(
    val field: String?,
    val message: String?
)