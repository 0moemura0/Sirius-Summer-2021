package com.example.tinkoffproject.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateTransaction(
    val value: Int?,
    val isIncome: Boolean?,
    val ts: Long?,
    val currencyShortStr: String?,
    val walletId: Int?,
    val categoryId: Int?
) {
}
