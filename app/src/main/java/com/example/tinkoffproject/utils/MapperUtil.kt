package com.example.tinkoffproject.utils

import android.graphics.Color
import com.example.tinkoffproject.R
import com.example.tinkoffproject.data.dto.to_view.Category
import com.example.tinkoffproject.data.dto.to_view.Currency
import com.example.tinkoffproject.data.dto.to_view.Transaction
import com.example.tinkoffproject.data.dto.response.CategoryNetwork
import com.example.tinkoffproject.data.dto.response.CurrencyNetwork
import com.example.tinkoffproject.data.dto.response.TransactionNetwork
import java.util.*
import kotlin.math.roundToInt
import kotlin.random.Random

enum class CategoryEnum(val remoteIconId: Int, val localIconId: Int) {
    SHOP(0, R.drawable.ic_shop),
    PLAIN(1, R.drawable.ic_plain),
    DIAMOND(2, R.drawable.ic_diamond),
    CAFE(3, R.drawable.ic_cafe),
    MEDICINE(4, R.drawable.ic_medicine),
    CUP(5, R.drawable.ic_cup),
    WIFI(6, R.drawable.ic_wi_fi),
    THEATER(7, R.drawable.ic_theater),
    TRAIN(8, R.drawable.ic_train),
    PET(9, R.drawable.ic_pet),
    SPORT(10, R.drawable.ic_sport),
    NET(11, R.drawable.ic_net),
    BUS(12, R.drawable.ic_bus),
    PALMA(13, R.drawable.ic_palma),
    HANDS(14, R.drawable.ic_hands),
    MUSIC(15, R.drawable.ic_music),
    CAP(16, R.drawable.ic_cap),
    GIFT(17, R.drawable.ic_gift),
    PHONE(18, R.drawable.ic_phone),
    GARDEN(19, R.drawable.ic_garden),
    MOVIE(20, R.drawable.ic_movie),
    CAR(21, R.drawable.ic_car),
    MEDICINE_BAG(22, R.drawable.ic_medicine_bag),
    EDUCATION(23, R.drawable.ic_education),
    TV(24, R.drawable.ic_tv),
    ICON(25, R.drawable.ic_icon),
    WEAR(26, R.drawable.ic_wear),
    MONEY(27, R.drawable.ic_wear),
    BALL0ON(28, R.drawable.ic_balloon),
    DOTS(29, R.drawable.ic_dots);

    companion object {
        val locals = values().map { it.localIconId }
        private val mapLocal = values().associateBy(CategoryEnum::localIconId)
        private val mapRemote = values().associateBy(CategoryEnum::remoteIconId)
        fun fromLocalId(id: Int) = mapLocal[id] ?: DEFAULT_CATEGORY_ENUM
        fun fromRemoteId(id: Int) = mapRemote[id] ?: DEFAULT_CATEGORY_ENUM
    }
}

val DEFAULT_CATEGORY_ENUM: CategoryEnum = CategoryEnum.DOTS


const val DEFAULT_COLOR = "#2E0EAE"
const val WALLET_COLOR = DEFAULT_COLOR

val WALLET_AS_CATEGORY = Category(
    id = 0,
    name = "Кошелёк",
    resIconId = R.drawable.ic_wallet,
    color = Color.parseColor(DEFAULT_COLOR),
    isIncome = false
)

val DEFAULT_CATEGORY = Category(
    id = 0,
    name = "Категория",
    resIconId = DEFAULT_CATEGORY_ENUM.localIconId,
    color = Color.parseColor(WALLET_COLOR),
    isIncome = false
)

val DEFAULT_CURRENCY = randomCurrency()

private fun randomCurrency() = Currency(
    rate = Random.nextDouble(0.0, 100.0),
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
    id = 0
)

fun colorToStr(color: Int) = String.format("#%06X", 0xFFFFFF and color)

fun CategoryNetwork.toCategory() = Category(
    name = name,
    resIconId = CategoryEnum.fromRemoteId(iconId).localIconId,
    color = Color.parseColor(iconColor),
    isIncome = isIncome,
    id = 0
)

fun TransactionNetwork.toTransaction(currency: CurrencyNetwork) = Transaction(
    id = id.toLong(),
    date = System.currentTimeMillis(), //TODO
    isIncome = isIncome,
    category = category?.toCategory(),
    amount = value.roundToInt(),
    amountFormatted = formatMoney(value, currency)
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
    symbol = ""
)

fun CurrencyNetwork.toLocal() = Currency(
    rate = DEFAULT_CURRENCY.rate,
    longName = longStr,
    shortName = shortStr,
    isUp = DEFAULT_CURRENCY.isUp
)