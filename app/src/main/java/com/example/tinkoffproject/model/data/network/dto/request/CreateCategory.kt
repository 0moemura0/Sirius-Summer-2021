package com.example.tinkoffproject.model.data.network.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateCategory(
    val isIncome: Boolean?,
    val iconId: Int?,
    val iconColor: String?,
    val name: String?,
)
