package com.example.tinkoffproject.model.utils

import android.util.Log
import com.example.tinkoffproject.model.data.dto.Currency
import java.util.*

fun formatMoney(amount: Int, currency: Currency): String {
    val currencySymbol =
        java.util.Currency.getInstance(currency.shortName).getSymbol(Locale.getDefault())
    return "${formatMoney(amount)} $currencySymbol"
}

private const val SPACE = ' '
fun formatMoney(amount: Int): String {
    val builder = StringBuilder()
    builder.append(amount)
    var numberCount = 0
    var myIndex = builder.length - 1
    for (i in builder.length - 1 downTo 0) {
        if (numberCount == 2) {
            builder.insert(myIndex, SPACE)
            myIndex++
            numberCount = 0
        } else numberCount++
        myIndex--
    }
    return builder.toString()
}
