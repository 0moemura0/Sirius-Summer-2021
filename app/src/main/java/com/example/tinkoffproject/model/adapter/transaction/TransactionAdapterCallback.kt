package com.example.tinkoffproject.model.adapter.transaction

import com.example.tinkoffproject.model.dto.Transaction

interface TransactionAdapterCallback {
    fun removed(from: Int)
    fun added(to: Int, new: Transaction)
    fun rangeRemoved(from: Int, to: Int)
    fun rangeAdded(to: Int, new: List<Transaction>)
}