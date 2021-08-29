package com.example.tinkoffproject.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class IncomeAndExpense(
    val income: Double?,
    val expenses: Double?
)