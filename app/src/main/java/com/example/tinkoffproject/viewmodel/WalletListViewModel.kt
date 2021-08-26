package com.example.tinkoffproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tinkoffproject.model.data.dto.Currency
import com.example.tinkoffproject.model.data.dto.Wallet
import com.example.tinkoffproject.model.data.mapper.DEFAULT_CURRENCY
import com.example.tinkoffproject.model.utils.State

class WalletListViewModel : ViewModel() {
    private val _wallets = MutableLiveData<State<List<Wallet>>>(
        State.DataState(
            listOf(
                Wallet(
                    id = 0, name = "Кошелёк 1", incomeAmount = 10000964, expensesAmount = 10,
                    currency = DEFAULT_CURRENCY,
                    limit = 11
                ),
            )
        )
    )
    val wallets: LiveData<State<List<Wallet>>> = _wallets

    /*val balance:LiveData<Int> = Transformations.map(_wallets) { w ->
        if (w is State.DataState) {
            w.data.sumOf {it.ba }
        } else 0
    }

    val expensesAmount:LiveData<Int> = Transformations.map(_wallets) { w ->
        if (w is State.DataState) {
            w.data.sumOf { it. }
        } else 0
    }*/

    val currency: MutableLiveData<List<Currency>> = MutableLiveData()

    fun loadCurrencyInfo() {
        currency.value = listOf(
            Currency(shortName = "USD", longName = "Доллар США", isUp = false, rate = 72.65),
            Currency(shortName = "EUR", longName = "Евро", isUp = false, rate = 86.60),
            Currency(shortName = "CHF", longName = "Швейцарские франки", isUp = true, 80.17),
        )
    }
}