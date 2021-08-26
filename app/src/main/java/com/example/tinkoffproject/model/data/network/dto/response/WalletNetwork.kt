package com.example.tinkoffproject.model.data.network.dto.response

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class WalletNetwork(
    @PrimaryKey
    val id: Int,
    val limit: Int,
    val name: String,
    @Embedded
    val currency: CurrencyNetwork,
    val balance: Double
)