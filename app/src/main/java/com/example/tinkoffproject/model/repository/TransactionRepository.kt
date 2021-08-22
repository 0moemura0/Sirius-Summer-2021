package com.example.tinkoffproject.model.repository

import com.example.tinkoffproject.model.data.network.dto.CategoryNetwork
import com.example.tinkoffproject.model.data.network.dto.TransactionNetwork

class TransactionRepository {
    fun getTransaction(): List<TransactionNetwork> {
        return listOf(
            TransactionNetwork(
                id = 10,
                date = 1629294379553,
                isIncome = true,
                category = CategoryNetwork("Супермаркет", 0, "F52222", isIncome = true),
                amount = 10000,
            ),
            TransactionNetwork(
                id = 10,
                date = 1629208049321,
                isIncome = false,
                category = CategoryNetwork("Зарплата", 1, "00B92D", isIncome = false),
                amount = 10000,
            )
        )
    }
}