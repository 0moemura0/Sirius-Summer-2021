package com.example.tinkoffproject.ui.main.adapter.transaction

import com.example.tinkoffproject.data.dto.response.TransactionNetwork

interface TransactionAdapterCallback {
    fun removed(from: Int)
    fun added(to: Int, new: TransactionNetwork)
    fun rangeRemoved(from: Int, to: Int)
    fun rangeAdded(to: Int, new: List<TransactionNetwork>)
}