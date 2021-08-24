package com.example.tinkoffproject.model.data.network.dto.response
import kotlinx.serialization.Serializable

@Serializable
data class TransactionNetwork (
    val id: Int?,
    val value: Double?,
    val isIncome: Boolean?,//"True если эта транзакция является доходом, false если расходом"
    val ts: String?, // "Дата совершения транзакции", "format": "date-time"
    val currency: CurrencyNetwork?,
    val category: CategoryNetwork?
)