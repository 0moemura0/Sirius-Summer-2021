package com.example.tinkoffproject.model.dto

data class Transaction(
    val id: Long,
    val date: Long,
    val type: TransactionType,
    val category: TransactionCategory,
    val amount: Int,
)