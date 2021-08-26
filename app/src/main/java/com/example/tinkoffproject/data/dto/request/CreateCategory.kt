package com.example.tinkoffproject.data.dto.request
import kotlinx.serialization.*

@Serializable
data class CreateCategory (
    val isIncome: Boolean?,
    val iconId: Int?,
    val iconColor: String?,
    val name: String?,
)
