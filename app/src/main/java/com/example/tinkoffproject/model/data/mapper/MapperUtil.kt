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

enum class IconEnum(val remoteIconId: Int, val localIconId: Int) {
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
    WALLET(25, R.drawable.ic_wallet),
    WEAR(26, R.drawable.ic_wear),
    MONEY(27, R.drawable.ic_wear),
    BALL0ON(28, R.drawable.ic_balloon),
    DOTS(29, R.drawable.ic_dots),

    //Не могуть быть кастомными
    GASOLINE(30, R.drawable.ic_gasoline),
    HOME(31, R.drawable.ic_home),
    SALARY(32, R.drawable.ic_salary),
    PERCENT(33, R.drawable.ic_percent);

    companion object {
        private val locals = values().map { it.localIconId }
        val customLocalsId = locals.filter { it < 30 }
        private val mapLocal = values().associateBy(IconEnum::localIconId)
        private val mapRemote = values().associateBy(IconEnum::remoteIconId)
        fun fromLocalId(id: Int) = mapLocal[id] ?: DEFAULT_ICON_ENUM
        fun fromRemoteId(id: Int) = mapRemote[id] ?: DEFAULT_ICON_ENUM
    }
}

val DEFAULT_ICON_ENUM: IconEnum = IconEnum.DOTS

const val DEFAULT_COLOR = "#5833EE"
const val WALLET_COLOR = DEFAULT_COLOR

val WALLET_AS_CATEGORY = Category(
        name = "Кошелёк",
        resIconId = IconEnum.WALLET.localIconId,
        color = Color.parseColor(DEFAULT_COLOR),
        isIncome = false
)

val DEFAULT_CATEGORY = Category(
        name = "Категория",
        resIconId = DEFAULT_ICON_ENUM.localIconId,
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
        iconId = IconEnum.fromLocalId(resIconId).remoteIconId,
        iconColor = colorToStr(color),
        isIncome = isIncome,
        id = 0
)

fun colorToStr(color: Int) = String.format("#%06X", 0xFFFFFF and color)

fun CategoryNetwork.toCategory() = Category(
        name = name,
        resIconId = IconEnum.fromRemoteId(iconId).localIconId,
        color = Color.parseColor(iconColor),
        isIncome = isIncome
)

fun TransactionNetwork.toTransaction(currency: Currency) = Transaction(
        id = id.toLong(),
        date = System.currentTimeMillis(), //TODO
        isIncome = isIncome,
        category = category.toCategory(),
        amount = value.roundToInt(),
        amountFormatted = formatMoney(value.roundToInt(), currency)
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
        longName = longStr,
        shortName = shortStr,
        isUp = DEFAULT_CURRENCY.isUp
)