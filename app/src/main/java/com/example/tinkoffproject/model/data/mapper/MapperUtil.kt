package com.example.tinkoffproject.model.data.mapper

import android.graphics.Color
import com.example.tinkoffproject.R
import com.example.tinkoffproject.model.data.dto.Category
import com.example.tinkoffproject.model.data.dto.Currency
import com.example.tinkoffproject.model.data.dto.Transaction
import com.example.tinkoffproject.model.data.network.dto.CategoryNetwork
import com.example.tinkoffproject.model.data.network.dto.TransactionNetwork
import com.example.tinkoffproject.model.utils.formatMoney

fun getResId(categoryIconId: Int): Int {
    return when (categoryIconId) {
        //TODO delete this icons and download all others
        0 -> R.drawable.ic_expenses
        1 -> R.drawable.ic_income
        //TODO add error drawable
        else -> R.drawable.ic_income
    }
}

fun CategoryNetwork.toCategory() = Category(
    name = name,
    resIconId = getResId(iconId),
    color = Color.parseColor(color)
)


//мне не нравится, что amount превращается в стринг
fun TransactionNetwork.toTransaction(currency: Currency) = Transaction(
    id = id,
    date = date,
    isIncome = isIncome,
    category = category.toCategory(),
    amount = formatMoney(amount, currency)
)