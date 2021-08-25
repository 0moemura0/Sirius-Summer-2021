package com.example.tinkoffproject.model.data.network.dto.response

import androidx.room.*
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
    var categoryId: Int = 0
) {
    constructor() : this(
        0,
        0.0,
        false,
        "",
        CurrencyNetwork("", ""),
        CategoryNetwork(0, false, 0, "", ""),
        0
    )

}