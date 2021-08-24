package com.example.tinkoffproject.model.data.network.dto.response

import kotlinx.serialization.*

@Serializable
data class CategoryNetwork(
    val id: Int?,
    val isIncome: Boolean?,
    val iconId: Int?,
    val iconColor: String?,
    val name: String?
)
