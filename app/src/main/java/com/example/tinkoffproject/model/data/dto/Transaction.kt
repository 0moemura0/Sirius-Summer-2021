package com.example.tinkoffproject.model.data.dto

data class Transaction(
    val id: Long,
    val date: Long,
    val isIncome: Boolean,
    val category: Category,
    val amount: Int,
    val amountFormatted: String,
)