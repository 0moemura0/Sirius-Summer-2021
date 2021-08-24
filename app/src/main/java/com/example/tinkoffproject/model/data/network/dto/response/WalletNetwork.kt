package com.example.tinkoffproject.model.data.network.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class WalletNetwork(
    val id: Int?,
    val limit: Int?,
    val name: String?,
    val currency: CurrencyNetwork?,
    val balance: Double?
)