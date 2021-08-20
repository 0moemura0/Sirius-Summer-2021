package com.example.tinkoffproject.model.data.dto


data class Wallet(
    val id: Long,
    val name: String,
    val incomeAmount: Int,
    val expensesAmount: Int,
    val currency: Currency,
    val limit: Long?
)