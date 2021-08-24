package com.example.tinkoffproject.model.data.mapper

import android.graphics.Color
import com.example.tinkoffproject.R
import com.example.tinkoffproject.model.data.dto.Category
import com.example.tinkoffproject.model.data.dto.Currency
import com.example.tinkoffproject.model.data.dto.Transaction
import com.example.tinkoffproject.model.data.network.dto.response.CategoryNetwork
import com.example.tinkoffproject.model.data.network.dto.response.CurrencyNetwork
import com.example.tinkoffproject.model.data.network.dto.response.TransactionNetwork
import com.example.tinkoffproject.model.utils.formatMoney
import java.util.*
import kotlin.math.roundToInt
import kotlin.random.Random

enum class CategoryEnum(val remoteIconId: Int, val localIconId: Int) {
    SHOP(0, R.drawable.ic_shop),
    INCOME(1, R.drawable.ic_income),
    SPORT(2, R.drawable.ic_sport),
    DEFAULT(-1, R.drawable.ic_income),
    WALLET(-2, R.drawable.ic_wallet);
    companion object {
        private val mapLocal = values().associateBy(CategoryEnum::localIconId)
        private val mapRemote = values().associateBy(CategoryEnum::remoteIconId)
        fun fromLocalId(id: Int) = mapLocal[id] ?: DEFAULT
        fun fromRemoteId(id: Int) = mapRemote[id] ?: DEFAULT
    }
}
const val DEFAULT_COLOR = "#2E0EAE"

val WALLET_AS_CATEGORY = Category(
    name = "Кошелёк",
    resIconId = CategoryEnum.WALLET.localIconId,
    color = Color.parseColor(DEFAULT_COLOR),
    isIncome = false
)


val DEFAULT_CATEGORY = Category(
    name = "Категория",
    resIconId = CategoryEnum.DEFAULT.localIconId,
    color = Color.parseColor(DEFAULT_COLOR),
    isIncome = false
)

val DEFAULT_CURRENCY = randomCurrency()

private fun randomCurrency() = Currency(
    rate = Random.nextDouble(0.0, 100.0).toFloat(),
    shortName = java.util.Currency.getInstance(Locale.getDefault()).currencyCode,
    longName = java.util.Currency.getInstance(Locale.getDefault()).displayName,
    isUp = Random.nextBoolean()
)

//fun currencyFullStr(network: CurrencyNetwork) = "${network.longStr ?: DEFAULT_CURRENCY.longName} (${network.shortStr ?: DEFAULT_CURRENCY.shortName})"

fun Category.toNetwork() = CategoryNetwork(
    name = name,
    iconId = CategoryEnum.fromLocalId(resIconId).remoteIconId,
    iconColor = colorToStr(color),
    isIncome = isIncome,
    id = null
)

fun colorToStr(color: Int) = String.format("#%06X", 0xFFFFFF and color)

fun CategoryNetwork.toCategory() = Category(
    name = name ?: "",
    resIconId = CategoryEnum.fromRemoteId(iconId ?: CategoryEnum.DEFAULT.remoteIconId).localIconId,
    color = Color.parseColor(iconColor),
    isIncome = isIncome ?: false
)

fun TransactionNetwork.toTransaction(currency: Currency) = Transaction(
    id = (id ?: 0).toLong(),
    date = System.currentTimeMillis(), //TODO
    isIncome = isIncome ?: false,
    category = category?.toCategory() ?: DEFAULT_CATEGORY,
    amount = (value ?: 0.0).roundToInt(),
    amountFormatted = formatMoney((value ?: 0.0).roundToInt(), currency)
)


//TODO set real currency
fun Transaction.toNetwork(currency: Currency = DEFAULT_CURRENCY) = TransactionNetwork(
    id = id.toInt(),
    ts = "", //TODO
    isIncome = isIncome,
    category = category.toNetwork(),
    value = amount.toDouble(),
    currency = currency.toNetwork()
)

fun Currency.toNetwork() = CurrencyNetwork(
    shortStr = shortName,
    longStr = longName,
)

fun CurrencyNetwork.toCurrency() = Currency(
    rate = DEFAULT_CURRENCY.rate,
    longName = longStr ?: DEFAULT_CURRENCY.longName,
    shortName = shortStr ?: DEFAULT_CURRENCY.shortName,
    isUp = DEFAULT_CURRENCY.isUp
)