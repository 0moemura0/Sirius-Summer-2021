package com.example.tinkoffproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tinkoffproject.model.data.dto.Currency
import com.example.tinkoffproject.model.data.dto.Transaction
import com.example.tinkoffproject.model.data.dto.Wallet
import com.example.tinkoffproject.model.utils.State

class CardDetailsViewModel : ViewModel() {
    private val _transactions = MutableLiveData<State<List<Transaction>>>(State.LoadingState)
    val transaction: LiveData<State<List<Transaction>>> = _transactions

    private val _wallet = MutableLiveData<State<Wallet>>(
        State.DataState(
            Wallet(
                id = 0, name = "Кошелёк 1", incomeAmount = 10000964, expensesAmount = 10,
                currency = Currency("USD", 10F),
                limit = 11

            )
        )
    )
    val wallet: LiveData<State<Wallet>> = _wallet


}