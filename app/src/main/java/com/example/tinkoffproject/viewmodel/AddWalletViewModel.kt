package com.example.tinkoffproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tinkoffproject.model.data.dto.Currency

class AddWalletViewModel : ViewModel() {
    var name = MutableLiveData<String>()
    var currency = MutableLiveData<Currency>()
    var limit = MutableLiveData<Int>()

    val isNextAvailable: MutableLiveData<Boolean> = MutableLiveData(false)

    init {
        init()
    }

    fun init() {
        name = MutableLiveData()
        currency = MutableLiveData()
        limit = MutableLiveData()
        isNextAvailable.value = false
    }
}