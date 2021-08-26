package com.example.tinkoffproject.ui.main.data

import com.example.tinkoffproject.data.dto.to_view.Currency

data class SelectableCurrency(
    val currency: Currency,
    var isChecked: Boolean = false
)