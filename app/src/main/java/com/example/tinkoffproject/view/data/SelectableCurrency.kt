package com.example.tinkoffproject.view.data

import com.example.tinkoffproject.model.data.network.dto.CurrencyNetwork

data class SelectableCurrency(
    val currency: CurrencyNetwork,
    var isChecked: Boolean = false
)