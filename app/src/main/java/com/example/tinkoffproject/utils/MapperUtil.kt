package com.example.tinkoffproject.utils

import android.graphics.Color
import com.example.tinkoffproject.R
import com.example.tinkoffproject.data.dto.response.CategoryNetwork
import com.example.tinkoffproject.data.dto.response.CurrencyNetwork
import com.example.tinkoffproject.data.dto.response.TransactionNetwork
import com.example.tinkoffproject.data.dto.response.WalletNetwork
import com.example.tinkoffproject.data.dto.to_view.Category
import com.example.tinkoffproject.data.dto.to_view.Currency
import com.example.tinkoffproject.data.dto.to_view.Transaction
import com.example.tinkoffproject.data.dto.to_view.Wallet
import java.util.*
import kotlin.random.Random

enum class IconEnum(val remoteIconId: Int, val localIconId: Int, val selectable: Boolean = true) {
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
    GASOLINE(30, R.drawable.ic_gasoline, false),
    HOME(31, R.drawable.ic_home, false),
    SALARY(32, R.drawable.ic_salary, false),
    PERCENT(33, R.drawable.ic_percent, false);

    companion object {
        private val locals = values().map { it.localIconId }
        val customLocalsId = values().filter { it.selectable }.map { it.localIconId }
        private val mapLocal = values().associateBy(IconEnum::localIconId)
        private val mapRemote = values().associateBy(IconEnum::remoteIconId)
        fun fromLocalId(id: Int) = mapLocal[id] ?: DEFAULT_ICON_ENUM
        fun fromRemoteId(id: Int) = mapRemote[id] ?: DEFAULT_ICON_ENUM
    }
}

enum class COLOR(val color: Int, val colorSelected: Int) {
    PURPLE(R.color.purple, R.color.purple_selected),
    BLUE(R.color.blue, R.color.blue_selected),
    BROWN(R.color.brown, R.color.brown_selected),
    PINK(R.color.pink, R.color.pink_selected),
    GREEN(R.color.green, R.color.green_selected),
    ORANGE(R.color.orange, R.color.orange_selected),
    PURPLE_DARK(R.color.purple_dark, R.color.purple_dark_selected),
    YELLOW(R.color.yellow, R.color.yellow_selected),
    BLUE_MAIN(R.color.blue_main, R.color.blue_main_selected),
    GREEN_MAIN(R.color.green_main, R.color.green_main_selected);

    companion object
}
/*fun make(context: Context) = COLOR.values().forEach {
    //Log.d("kek", "${it.name} - ${colorToStr(manipulateColor(ContextCompat.getColor(context, it.color), 0.7f))}")
}
fun manipulateColor(color: Int, factor: Float): Int {
    val a: Int = Color.alpha(color)
    val r = (Color.red(color) * factor).roundToInt()
    val g = (Color.green(color) * factor).roundToInt()
    val b = (Color.blue(color) * factor).roundToInt()
    return Color.argb(
        a,
        min(r, 255),
        min(g, 255),
        min(b, 255)
    )
}*/

val DEFAULT_ICON_ENUM: IconEnum = IconEnum.DOTS

const val DEFAULT_COLOR = "#5833EE"
const val WALLET_COLOR = DEFAULT_COLOR
val DEFAULT_CURRENCY = randomCurrency()

private fun randomCurrency() = Currency(
    rate = Random.nextDouble(0.0, 100.0),
    shortName = java.util.Currency.getInstance(Locale.getDefault()).currencyCode,
    longName = java.util.Currency.getInstance(Locale.getDefault()).displayName,
    isUp = Random.nextBoolean(),
    symbol = ""
)

//fun currencyFullStr(network: CurrencyNetwork) = "${network.longStr ?: DEFAULT_CURRENCY.longName} (${network.shortStr ?: DEFAULT_CURRENCY.shortName})"


fun colorToStr(color: Int) = String.format("#%06X", 0xFFFFFF and color)

//wallet <--> transaction

fun Transaction.asWallet() = Wallet(
    id = id,
    name = "",
    incomeAmount = -1,
    expensesAmount = -1,
    currency = currency,
    limit = 0,
    balance = 0,
    hidden = false
)

fun Wallet.asTransaction() = Transaction(
    id = id,
    ts = 0,
    isIncome = false,
    category = Category(
        name = name, resIconId = IconEnum.WALLET.localIconId, color = Color.parseColor(
            DEFAULT_COLOR
        ), isIncome = false, id = 0
    ),
    value = (balance ?:0 ).toInt(),
    currency = currency,
    walletId = id,
    amountFormatted = formatMoney((balance ?:0 ).toInt(), currency.symbol),//TODO
    categoryId = id
)

//transaction
fun Transaction.toNetwork() = TransactionNetwork(
    id = id,
    value = value,
    isIncome = isIncome,
    ts = ts,
    currency = currency.toNetwork(),
    category = category.toNetwork(),
    categoryId = categoryId,
    walletId = walletId
)

fun TransactionNetwork.toLocal() = Transaction(
    id = id,
    value = value,
    ts = ts,
    isIncome = isIncome,
    category = category.toLocal(),
    categoryId = categoryId,
    amountFormatted = formatMoney(value, currency.symbol),
    currency = currency.toLocal(),
    walletId = walletId
)
//category

fun Category.toNetwork() = CategoryNetwork(
    id = id,
    isIncome = isIncome,
    iconId = IconEnum.fromLocalId(resIconId).remoteIconId,
    iconColor = colorToStr(color),
    name = name,
)

fun CategoryNetwork.toLocal() = Category(
    id = id,
    isIncome = isIncome,
    resIconId = IconEnum.fromRemoteId(iconId).localIconId,
    color = Color.parseColor(iconColor),
    name = name
)
//wallet

fun WalletNetwork.toLocal() = Wallet(
    id = id,
    name = name,
    incomeAmount = 0,
    expensesAmount = 0,
    currency = currency.toLocal(),
    limit = limit,
    balance = balance,
    hidden = hidden
)

fun Wallet.toNetwork() = WalletNetwork(
    id = id,
    name = name,
    limit = limit,
    currency = currency.toNetwork(),
    balance = balance,
    hidden = hidden
)
//currency

fun CurrencyNetwork.toLocal() = Currency(
    rate = DEFAULT_CURRENCY.rate,
    longName = longStr,
    shortName = shortStr,
    isUp = DEFAULT_CURRENCY.isUp,
    symbol = symbol
)

fun Currency.toNetwork() = CurrencyNetwork(
    longStr = longName,
    shortStr = shortName,
    symbol = symbol
)

