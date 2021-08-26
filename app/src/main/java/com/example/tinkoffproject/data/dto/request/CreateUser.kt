package com.example.tinkoffproject.data.dto.request

import kotlinx.serialization.*

@Serializable
data class CreateUser (
    val username: String?
)