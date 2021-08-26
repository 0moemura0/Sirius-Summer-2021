package com.example.tinkoffproject.data.dto.to_view

data class Currency(
    val shortName: String,
    val longName: String,
    val isUp: Boolean,
    val rate: Double
)