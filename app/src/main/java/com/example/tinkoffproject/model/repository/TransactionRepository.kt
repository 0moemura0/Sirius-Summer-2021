package com.example.tinkoffproject.model.repository

import com.example.tinkoffproject.model.dto.Transaction
import com.example.tinkoffproject.model.dto.TransactionCategory
import com.example.tinkoffproject.model.dto.TransactionType

class TransactionRepository {
    fun getTransaction(date: Long): List<Transaction> {
        return listOf(
            Transaction(
                id = 10,
                date = 1629294379553,
                type = TransactionType.INCOME,
                category = TransactionCategory.INCOME_PART_TIME_JOB,
                amount = 10000,
            ),
            Transaction(
                id = 10,
                date = 1629208049321,
                type = TransactionType.EXPENSES,
                category = TransactionCategory.INCOME_PART_TIME_JOB,
                amount = 10000,
            )
        )
    }
}