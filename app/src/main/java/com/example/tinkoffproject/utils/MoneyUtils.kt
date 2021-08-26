package com.example.tinkoffproject.utils

import com.example.tinkoffproject.data.dto.to_view.Currency
import java.util.Locale

fun formatMoney(amount: Int, symbol: String): String {
    return "${formatMoney(amount)} $symbol"
}

private const val SPACE = ' '
fun formatMoney(amount: Int): String {
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
