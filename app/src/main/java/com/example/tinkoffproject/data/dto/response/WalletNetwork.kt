package com.example.tinkoffproject.data.dto.response

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class WalletNetwork(
    @PrimaryKey
    var id: Int,
    val limit: Int,
    val name: String,
    @Embedded
    val currency: CurrencyNetwork,
    val balance: Int?,
    val hidden: Boolean
)