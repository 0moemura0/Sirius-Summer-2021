package com.example.tinkoffproject.utils

import com.example.tinkoffproject.data.dto.response.CurrencyNetwork
import java.util.*

fun formatMoney(amount: Double, currency: CurrencyNetwork): String {
    val javaCurr = Currency.getInstance(currency.shortStr)
    val currencySymbol =
        javaCurr.getSymbol(Locale.getDefault())
    //TODO рус не отображается, надо хранить где-то
    return "${formatMoney(amount)} $currencySymbol"
}

private const val SPACE = ' '
fun formatMoney(amount: Double): String {
    val putSpaceEveryNSymbols = 3
    val builder = StringBuilder()
    builder.append(amount)
    var numberCount = 1
    for (i in builder.length - 1 downTo 0) {
        if (numberCount == putSpaceEveryNSymbols) {
            builder.insert(i, SPACE)
            numberCount = 1
        } else numberCount++
    }
    return builder.toString()
}