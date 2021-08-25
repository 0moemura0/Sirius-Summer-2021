package com.example.tinkoffproject.view.data

import com.example.tinkoffproject.model.data.dto.Currency

data class SelectableCurrency(
    val currency: Currency,
    var isChecked: Boolean = false
)