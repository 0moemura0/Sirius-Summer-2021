package com.example.tinkoffproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tinkoffproject.model.data.dto.Currency
import com.example.tinkoffproject.model.data.dto.Wallet
import com.example.tinkoffproject.model.data.mapper.toCurrency
import com.example.tinkoffproject.model.data.network.dto.response.CurrencyNetwork

class AddWalletViewModel : ViewModel() {
    var name = MutableLiveData<String>()
    var currency = MutableLiveData<Currency>()
    var limit = MutableLiveData<Int>()

    init {
        init()
    }

    fun init() {
        name = MutableLiveData()
        currency = MutableLiveData()
        limit = MutableLiveData()
    }

    fun getAllCurrency(): List<Currency>{
        return listOf(
            CurrencyNetwork("RUS", "Российский рубль"),
            CurrencyNetwork("USD", "Доллар США"),
            CurrencyNetwork("EUR", "Евро"),
            CurrencyNetwork("CHF", "Швейцарские франки"),
            CurrencyNetwork("KWD", "Кувейтский динар"),
            CurrencyNetwork("BHD", "Бахрейнский динар"),
            CurrencyNetwork("OMR", "Оманский риал"),
            CurrencyNetwork("JPY", "Японская иена"),
            CurrencyNetwork("SEK", "Шведская крона")
        ).map { it.toCurrency() }
    }

    fun addWallet():Boolean{
        return true//Wallet()
    }
}