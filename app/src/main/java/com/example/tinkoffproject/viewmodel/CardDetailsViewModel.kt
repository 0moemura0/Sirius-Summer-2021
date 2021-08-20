package com.example.tinkoffproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tinkoffproject.model.data.dto.Transaction
import com.example.tinkoffproject.model.data.dto.Wallet
import com.example.tinkoffproject.model.utils.State

class CardDetailsViewModel : ViewModel() {
    private val _transactions = MutableLiveData<State<List<Transaction>>>(State.LoadingState)
    val transaction: LiveData<State<List<Transaction>>> = _transactions

    private val _wallet = MutableLiveData<State<Wallet>>(State.LoadingState)
    val wallet: LiveData<State<Wallet>> = _wallet

    /*val expensesSum: LiveData<Int> = Transformations.map(transaction) { transactions ->
        if (transactions is State.DataState)
            transactions.data.filter { !it.isIncome }.sumOf { it.amount }
        else 0
    }

    val incomeSum: LiveData<Int> = Transformations.map(transaction) { transactions ->
        if (transactions is State.DataState)
            transactions.data.filter { it.isIncome }.sumOf { it.amount }
        else 0
    }*/


}