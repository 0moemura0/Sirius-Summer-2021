package com.example.tinkoffproject.data.dto.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.*

@Entity
@Serializable
data class CurrencyNetwork(
    @PrimaryKey
    val shortStr: String,
    val longStr: String,
    val symbol: String
)