package com.example.tinkoffproject.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateUser(
    val username: String?
)