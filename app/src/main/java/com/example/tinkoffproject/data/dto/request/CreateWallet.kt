package com.example.tinkoffproject.data.dto.request

import kotlinx.serialization.*

@Serializable
class CreateWallet(
    val limit: Int?,
    val name: String?,
    val currencyShortStr: String?,
)