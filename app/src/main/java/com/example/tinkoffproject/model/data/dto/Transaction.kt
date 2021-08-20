package com.example.tinkoffproject.model.data.dto

//мда... val amount: String,
data class Transaction(
    val id: Long,
    val date: Long,
    val isIncome: Boolean,
    val category: Category,
    val amount: String,
)