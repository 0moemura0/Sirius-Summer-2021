package com.example.tinkoffproject.model.data.network.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateUser(
    val username: String?
)