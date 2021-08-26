package com.example.tinkoffproject.data.dto.to_view


data class Wallet(
    val id: Long,
    val name: String,
    val incomeAmount: Int,
    val expensesAmount: Int,
    val currency: Currency,
    val limit: Int?
)