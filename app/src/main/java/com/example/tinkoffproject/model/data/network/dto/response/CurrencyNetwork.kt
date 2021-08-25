package com.example.tinkoffproject.model.data.network.dto.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class CurrencyNetwork(
    @PrimaryKey
    val shortStr: String,
    val longStr: String
)