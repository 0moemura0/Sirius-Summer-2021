package com.example.tinkoffproject.model.data.network.dto.response
import kotlinx.serialization.*

@Serializable
data class CurrencyNetwork (
    val shortStr: String?,
    val longStr: String?
)