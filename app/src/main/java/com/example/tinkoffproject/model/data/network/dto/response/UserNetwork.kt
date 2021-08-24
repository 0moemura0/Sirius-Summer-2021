package com.example.tinkoffproject.model.data.network.dto.response
import kotlinx.serialization.*

@Serializable
data class UserNetwork(
    val id: Int?,
    val username: String?
)