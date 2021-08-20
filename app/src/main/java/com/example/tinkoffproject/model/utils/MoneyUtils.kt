package com.example.tinkoffproject.model.utils

import android.util.Log
import com.example.tinkoffproject.model.data.dto.Currency
import java.util.*

fun formatMoney(amount: Int, currency: Currency): String {
    val javaCurr = java.util.Currency.getInstance(currency.shortName)
    val currencySymbol =
        javaCurr.getSymbol(Locale.getDefault())
    //TODO рус не отображается, надо хранить где-то
    Log.d("kek", "$currencySymbol - ${javaCurr.displayName} - ${javaCurr.symbol}")
    Log.d("kek", "$currencySymbol - ${java.util.Currency.getInstance("USD").symbol} - ${javaCurr.symbol}")

    return "${formatMoney(amount)} $currencySymbol"
}

private const val SPACE = ' '
fun formatMoney(amount: Int): String {
    val builder = StringBuilder()
    builder.append(amount)
    var numberCount = 0
    for (i in builder.length - 1 downTo 0) {
        if (numberCount == 2) {
            builder.insert(i, SPACE)
            numberCount = 0
        } else numberCount++
    }
    return builder.toString()
}
