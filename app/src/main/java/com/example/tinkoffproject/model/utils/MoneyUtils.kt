package com.example.tinkoffproject.model.utils

import com.example.tinkoffproject.model.data.dto.Currency
import java.text.DecimalFormat
import java.util.*

fun formatMoney(amount: Int, currency: Currency): String {
    val currencySymbol =
        java.util.Currency.getInstance(currency.shortName).getSymbol(Locale.getDefault())
    return "${formatMoney(amount)} $currencySymbol"
}

// :'(
//TODO normal format or set max value
//
private val moneyFormat = DecimalFormat("### ".repeat(10))

fun formatMoney(amount: Int): String =
    amount.toString().format(moneyFormat)
