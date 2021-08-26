package com.example.tinkoffproject.data.dto.response

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Ignore
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Entity(
    foreignKeys = [ForeignKey(
        entity = CategoryNetwork::class,
        parentColumns = ["id"],
        childColumns = ["categoryId"]
    )]
)
@Serializable
data class TransactionNetwork(
    @PrimaryKey
    var id: Int,
    var value: Double,
    var isIncome: Boolean,//"True если эта транзакция является доходом, false если расходом"
    var ts: String, // "Дата совершения транзакции", "format": "date-time"
    @Embedded
    var currency: CurrencyNetwork,
    @Ignore
    var category: CategoryNetwork,
    @Transient
    var categoryId: Int = 0,
    @Transient
    var walletId: Int = 0
) {
    constructor() : this(
        0,
        0.0,
        false,
        "",
        CurrencyNetwork("", "", ""),
        CategoryNetwork(0, false, 0, "", ""),
        0, 0
    )

}