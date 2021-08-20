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
    Log.d("kek", "format - $amount")
    val builder = StringBuilder()
    builder.append(amount)
    var numberCount = 0
    for (i in builder.length - 1 downTo 0) {
        Log.d("kek", "i - $i, count - $numberCount, myIndex - $i")
        if (numberCount == 2) {
            builder.insert(i, SPACE)
            numberCount = 0
        } else numberCount++
    }
    return builder.toString()
}
