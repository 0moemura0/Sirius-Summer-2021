package com.example.tinkoffproject.model.data.network.dto

data class TransactionNetwork(
    val id: Long,
    val date: Long,
    val isIncome: Boolean,
    val category: CategoryNetwork,
    val amount: Int,
)