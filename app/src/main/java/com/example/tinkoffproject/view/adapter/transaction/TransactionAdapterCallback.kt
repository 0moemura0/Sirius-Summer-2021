package com.example.tinkoffproject.view.adapter.transaction

import com.example.tinkoffproject.model.data.network.dto.response.TransactionNetwork

interface TransactionAdapterCallback {
    fun removed(from: Int)
    fun added(to: Int, new: TransactionNetwork)
    fun rangeRemoved(from: Int, to: Int)
    fun rangeAdded(to: Int, new: List<TransactionNetwork>)
}