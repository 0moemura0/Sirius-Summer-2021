package com.example.tinkoffproject.model.adapter.transaction

import com.example.tinkoffproject.model.data.network.dto.TransactionNetwork

interface TransactionAdapterCallback {
    fun removed(from: Int)
    fun added(to: Int, new: TransactionNetwork)
    fun rangeRemoved(from: Int, to: Int)
    fun rangeAdded(to: Int, new: List<TransactionNetwork>)
}