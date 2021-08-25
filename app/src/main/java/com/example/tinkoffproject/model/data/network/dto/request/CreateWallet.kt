package com.example.tinkoffproject.model.data.network.dto.request

import kotlinx.serialization.Serializable

@Serializable
class CreateWallet(
    val limit: Int?,
    val name: String?,
    val currencyShortStr: String?,
)