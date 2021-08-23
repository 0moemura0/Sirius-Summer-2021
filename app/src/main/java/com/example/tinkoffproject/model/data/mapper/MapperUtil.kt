package com.example.tinkoffproject.model.data.mapper

import android.graphics.Color
import com.example.tinkoffproject.R
import com.example.tinkoffproject.model.data.dto.Category
import com.example.tinkoffproject.model.data.dto.Currency
import com.example.tinkoffproject.model.data.dto.Transaction
import com.example.tinkoffproject.model.data.network.dto.CategoryNetwork
import com.example.tinkoffproject.model.data.network.dto.TransactionNetwork
import com.example.tinkoffproject.model.utils.formatMoney

enum class CategoryEnum(val remoteIconId: Int, val localIconId: Int) {
    SHOP(0, R.drawable.ic_shop),
    INCOME(1, R.drawable.ic_income),
    SPORT(2, R.drawable.ic_sport),
    DEFAULT(-1, R.drawable.ic_income);

    companion object {
        private val mapLocal = values().associateBy(CategoryEnum::localIconId)
        private val mapRemote = values().associateBy(CategoryEnum::remoteIconId)
        fun fromLocalId(id: Int) = mapLocal[id] ?: DEFAULT
        fun fromRemoteId(id: Int) = mapRemote[id] ?: DEFAULT
    }
}

fun Category.toNetwork() = CategoryNetwork(
    name = name,
    iconId = CategoryEnum.fromLocalId(resIconId).remoteIconId,
    color = colorToStr(color),
    isIncome = isIncome
)

fun colorToStr(color: Int) = String.format("#%06X", 0xFFFFFF and color)



fun CategoryNetwork.toCategory() = Category(
    name = name,
    resIconId = CategoryEnum.fromRemoteId(iconId).localIconId,
    color = Color.parseColor(color),
    isIncome = isIncome

)

fun TransactionNetwork.toTransaction(currency: Currency) = Transaction(
    id = id,
    date = date,
    isIncome = isIncome,
    category = category.toCategory(),
    amount = amount,
    amountFormatted = formatMoney(amount, currency)
)

fun Transaction.toNetwork() = TransactionNetwork(
    id = id,
    date = date,
    isIncome = isIncome,
    category = category.toNetwork(),
    amount = amount
)