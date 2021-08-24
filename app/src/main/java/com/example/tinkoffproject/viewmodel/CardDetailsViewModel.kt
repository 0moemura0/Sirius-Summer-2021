package com.example.tinkoffproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tinkoffproject.R
import com.example.tinkoffproject.model.data.dto.Category
import com.example.tinkoffproject.model.data.dto.Currency
import com.example.tinkoffproject.model.data.dto.Transaction
import com.example.tinkoffproject.model.data.dto.Wallet
import com.example.tinkoffproject.model.data.mapper.DEFAULT_CURRENCY
import com.example.tinkoffproject.model.utils.State

class CardDetailsViewModel : ViewModel() {
    private val _transactions = MutableLiveData<State<List<Transaction>>>(State.DataState(listOf(
        Transaction(0, 112213214, false, Category("Имя", R.color.blue_main, 1, false), 123, "235 P"),
        Transaction(0, 112213214, false, Category("Имя", R.color.blue_main, 1, false), 123, "235 P"),
        Transaction(0, 112213214, false, Category("Имя", R.color.blue_main, 1, false), 123, "235 P"),
        Transaction(0, 112213214, false, Category("Имя", R.color.blue_main, 1, false), 123, "235 P"),
        Transaction(0, 112213214, false, Category("Имя", R.color.blue_main, 1, false), 123, "235 P"),
        Transaction(0, 112213214, false, Category("Имя", R.color.blue_main, 1, false), 123, "235 P"),
        Transaction(0, 112213214, false, Category("Имя", R.color.blue_main, 1, false), 123, "235 P"),
        Transaction(0, 112213214, false, Category("Имя", R.color.blue_main, 1, false), 123, "235 P"),
        )))
    val transaction: LiveData<State<List<Transaction>>> = _transactions

    private val _wallet = MutableLiveData<State<Wallet>>(
        State.DataState(
            Wallet(
                id = 0, name = "Кошелёк 1", incomeAmount = 10000964, expensesAmount = 10,
                currency = DEFAULT_CURRENCY,
                limit = 11

            )
        )
    )
    val wallet: LiveData<State<Wallet>> = _wallet


}